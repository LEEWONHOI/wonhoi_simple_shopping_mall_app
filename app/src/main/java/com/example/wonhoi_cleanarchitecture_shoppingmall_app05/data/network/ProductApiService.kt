package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.network

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.response.ProductListResponse
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProductList(): Response<ProductListResponse>

    @GET("products/{productId}")
    suspend fun getProduct(
        @Path("productId")
        productId : Long
    ) : Response<ProductResponse>

}