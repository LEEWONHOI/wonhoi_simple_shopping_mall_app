package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.databinding.ActivityProductDetailBinding
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions.load
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions.loadCenterCrop
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions.toast
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

internal class ProductDetailActivity : BaseActivity<ProductDetailViewModel, ActivityProductDetailBinding>() {

    companion object {
        const val PRODUCT_ID_KEY = "PRODUCT_ID_KEY"

        const val PRODUCT_ORDERED_RESULT_CODE = 99

        fun newIntent(context: Context, productId : Long) =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID_KEY, productId)
            }

    }

    override val viewModel by inject<ProductDetailViewModel> {
        parametersOf(
            intent.getLongExtra(PRODUCT_ID_KEY, -1)
        )
    }

    override fun getViewBinding(): ActivityProductDetailBinding
    = ActivityProductDetailBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.productDetailStateLiveData.observe(this) { productDetailState ->
        when(productDetailState) {
            is ProductDetailState.UnInitialized -> initViews()
            is ProductDetailState.Loading -> handleLoading()
            is ProductDetailState.Success -> handleSuccess(productDetailState)
            is ProductDetailState.Error -> handleError()
            is ProductDetailState.Order -> handleOrder()
        }
    }

    private fun initViews() = with(binding) {
        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        title = ""
        toolbar.setNavigationOnClickListener {
            finish()
        }

        orderButton.setOnClickListener {
            viewModel.orderProduct()
        }
    }

    private fun handleLoading() = with(binding) {
        progressBar.isVisible = true
    }

    private fun handleSuccess(state: ProductDetailState.Success) = with(binding) {
        progressBar.isGone = true
        val product = state.productEntity
        title = product.productName
        productCategoryTextView.text = product.productType
        productImageView.loadCenterCrop(product.productImage, 8f)
        productPriceTextView.text = "${product.productPrice}円"
        introductionImageView.load(state.productEntity.productImage)
    }

    private fun handleError() {
        toast("The product information could not be retrieved.")
        finish()
    }

    private fun handleOrder() {
        setResult(PRODUCT_ORDERED_RESULT_CODE)
        toast("Your order has been completed successfully.")
        finish()
    }


}