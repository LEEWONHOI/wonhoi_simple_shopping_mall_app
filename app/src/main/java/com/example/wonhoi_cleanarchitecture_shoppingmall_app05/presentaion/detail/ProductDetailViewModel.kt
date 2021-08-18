package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.GetProductItemUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.OrderProductItemUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductDetailViewModel(
    private val productId: Long,
    private val getProductItemUseCase: GetProductItemUseCase,
    private val orderProductItemUseCase: OrderProductItemUseCase
) : BaseViewModel() {

    private var _productDetailStateLiveData =
        MutableLiveData<ProductDetailState>(ProductDetailState.UnInitialized)
    val productDetailStateLiveData: LiveData<ProductDetailState> = _productDetailStateLiveData

    private lateinit var productEntity: ProductEntity // use for order complete

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ProductDetailState.Loading)
        getProductItemUseCase(productId)?.let { getProduct ->
            productEntity = getProduct
            setState(
                ProductDetailState.Success(getProduct)
            )
        } ?: kotlin.run {
            setState(ProductDetailState.Error)
        }
    }

    fun orderProduct() = viewModelScope.launch {
        if (::productEntity.isInitialized) {
            val productId = orderProductItemUseCase(productEntity)
            if (productEntity.id == productId) {
                setState(ProductDetailState.Order)
            }
        } else {
            setState(ProductDetailState.Error)
        }


    }

    private fun setState(state: ProductDetailState) {
        _productDetailStateLiveData.postValue(state)
    }

}