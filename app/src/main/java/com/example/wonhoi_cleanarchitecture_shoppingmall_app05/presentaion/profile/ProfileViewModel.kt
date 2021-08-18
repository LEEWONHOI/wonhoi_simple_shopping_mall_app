package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.preference.PreferenceManager
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.DeleteOrderedProductListUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.GetOrderedProductListUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ProfileViewModel(
    private val preferenceManager: PreferenceManager,
    private val getOrderedProductListUseCase: GetOrderedProductListUseCase,
    private val deleteOrderedProductListUseCase: DeleteOrderedProductListUseCase
    ) : BaseViewModel() {

    val _profileStateLiveData = MutableLiveData<ProfileState>(ProfileState.Uninitialized)
    val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ProfileState.Loding)
        preferenceManager.getIdToken()?.let { loginToken ->
            setState(
                ProfileState.Login(loginToken)
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }
    }

    private fun setState(state: ProfileState) {
        _profileStateLiveData.postValue(state)
    }

    fun saveToken(idToken: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            preferenceManager.putIdToken(idToken)
            fetchData()
        }
    }

    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch {
        firebaseUser?.let { user ->
            setState(
                ProfileState.Success.Registered(
                    user.displayName ?: "Unkown",
                    user.photoUrl,
                    //ordered product list
                    getOrderedProductListUseCase()
                )
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }
    }

    fun signOut() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            preferenceManager.removeIdToken()
        }
        deleteOrderedProductListUseCase()
        fetchData()
    }


}