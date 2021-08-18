package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.R
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.databinding.FragmentProfileBinding
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions.loadCenterCrop
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions.toast
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseFragment
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.adapter.ProductListAdapter
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.detail.ProductDetailActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject

internal class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    override val viewModel by inject<ProfileViewModel>()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val gsc by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    task.getResult(ApiException::class.java)?.let { account ->
                        Log.d("test", "firebaseAuthWithGoogle:" + account.id)
                        viewModel.saveToken(account.idToken ?: throw Exception())
                    } ?: throw Exception()
                } catch (e: Exception) {
                    e.printStackTrace()
                    handleErrorState()
                }
            } else {
                Log.d("test", "$result")
            }
        }


    private val adapter = ProductListAdapter()

    override fun observeData() = viewModel.profileStateLiveData.observe(this) { profileState ->
        when (profileState) {
            is ProfileState.Uninitialized -> initViews(binding)
            is ProfileState.Loding -> handleLoadingState()
            is ProfileState.Login -> handleLoginState(profileState)
            is ProfileState.Success -> handleSuccessState(profileState)
            is ProfileState.Error -> handleErrorState()
        }
    }

    private fun initViews(binding: FragmentProfileBinding) = with(binding) {
        recyclerView.adapter = adapter
        loginButton.setOnClickListener {
            signInGoogle()
        }
        logoutButton.setOnClickListener {
            viewModel.signOut()
        }
    }

    private fun handleLoadingState() = with(binding) {
        progressBar.isVisible = true
        loginRequiredGroup.isGone = true
    }

    private fun handleSuccessState(state : ProfileState) = with(binding) {
        progressBar.isGone = true
        when (state) {
            is ProfileState.Success.Registered -> {
                handleRegisteredState(state)
            }
            is ProfileState.Success.NotRegistered -> {
                profileGroup.isGone = true
                loginRequiredGroup.isVisible = true
            }
            else -> {
                profileGroup.isGone = true
                loginRequiredGroup.isVisible = true
                emptyResultTextView.text = "Login error"
            }
        }
    }

    private fun signInGoogle() {
        val signInIntent : Intent = gsc.signInIntent
        loginLauncher.launch(signInIntent)
    }

    private fun handleLoginState(state: ProfileState.Login) = with(binding) {
        binding.progressBar.isVisible = true
        val credentail = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credentail)
            .addOnCompleteListener(requireActivity()) {
                if(it.isSuccessful) { // login state
                    val user = firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                } else {    // logout state
                    viewModel.setUserInfo(null)
                }
            }
    }

    private fun handleRegisteredState(state : ProfileState.Success.Registered) = with(binding) {
        profileGroup.isVisible = true
        loginRequiredGroup.isGone = true
        profileImageView.loadCenterCrop(state.profileImageUri.toString(), 60f)
        userNameTextView.text = state.userName

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                startActivity(
                    ProductDetailActivity.newIntent(requireContext(), it.id)
                )
            }
        }
    }

    private fun handleErrorState() {
        requireContext().toast("Error")
    }


}