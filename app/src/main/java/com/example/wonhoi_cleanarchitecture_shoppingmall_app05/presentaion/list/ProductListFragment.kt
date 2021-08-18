package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.list

import android.content.Context
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.databinding.FragmentProductListBinding
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions.toast
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseFragment
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.adapter.ProductListAdapter
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.detail.ProductDetailActivity
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.main.MainActivity
import org.koin.android.ext.android.inject

internal class ProductListFragment : BaseFragment<ProductListViewModel, FragmentProductListBinding>() {

    companion object {
        const val TAG = "ProductListFragment"
    }

    override val viewModel by inject<ProductListViewModel>()

    override fun getViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    private val adapter = ProductListAdapter()

    private val startProductDetailForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result : ActivityResult ->
            //  Move and refresh profile after order completion
            if (result.resultCode == ProductDetailActivity.PRODUCT_ORDERED_RESULT_CODE) {
                (requireActivity() as MainActivity).viewModel.refreshOrderList()
            }

        }

    override fun observeData() = viewModel.productListStateLiveData.observe(this) { productListState ->
        when(productListState) {
            is ProductListState.UnInitialized -> {
                initViews(binding)
            }
            is ProductListState.Loading -> {
                handleLoadingState()
            }
            is ProductListState.Success -> {
                handleSuccessState(productListState)
            }
            is ProductListState.Error -> {
                handleErrorState()
            }
        }
    }

    private fun initViews(binding: FragmentProductListBinding) = with(binding) {
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state : ProductListState.Success) = with(binding) {
        refreshLayout.isRefreshing = false

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) { productEntity ->
                startProductDetailForResult.launch(
                    ProductDetailActivity.newIntent(requireContext(), productEntity.id)
                )

//            requireContext().toast("Product Entity : $productEntity")

            }
        }
    }

    private fun handleErrorState() {
        Toast.makeText(requireContext(), "An error has occurred.", Toast.LENGTH_SHORT).show()
    }

}