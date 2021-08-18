package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.repository

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.db.dao.ProductDao
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val productApi : ProductApiService,
    private val ioDispatcher: CoroutineDispatcher,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher)  {
        val response = productApi.getProductList()
        return@withContext if (response.isSuccessful) {
            response.body()?.items?.map { it.toEntity() } ?: listOf()
        } else {
            listOf()
        }
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher) {
    productDao.getAll()
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity? = withContext(ioDispatcher) {
        val response = productApi.getProduct(itemId)
        return@withContext if(response.isSuccessful) {
            response.body()?.toEntity()
        } else {
            null
        }
    }

    override suspend fun insertProductItem(productItem: ProductEntity): Long = withContext(ioDispatcher) {
        productDao.insert(productItem)
    }

    override suspend fun insertProductList(productItem: List<ProductEntity>) = withContext(ioDispatcher) {
        productDao.insert(productItem)
    }

    override suspend fun updateProductItem(productItem: ProductEntity) = withContext(ioDispatcher) {
        productDao.update(productItem)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        productDao.deleteAll()
    }

    override suspend fun deleteProductItem(id: Long) = withContext(ioDispatcher) {
        productDao.delete(id)
    }



}