package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.repository.ProductRepository

internal class DeleteOrderedProductListUseCase(
    private val productRepository : ProductRepository
) : UseCase {
    suspend operator fun invoke()  {
        return productRepository.deleteAll()
    }

}