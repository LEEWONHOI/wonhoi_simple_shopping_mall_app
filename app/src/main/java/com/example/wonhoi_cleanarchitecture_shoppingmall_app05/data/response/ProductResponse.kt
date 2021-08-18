package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.response

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity
import java.util.*

data class ProductResponse(
    val id:String,
    val createdAt : Long,
    val productName : String,
    val productPrice : String,
    val productImage : String,
    val productType : String,
    val productIntroductionImage : String
) {
    // Change Entity
    fun toEntity() : ProductEntity =
        ProductEntity(
            id = this.id.toLong(),
            createdAt = Date(this.createdAt),
            productName = this.productName,
            productPrice = this.productPrice.toDouble().toInt(), // check decimal point
            productImage = productImage,
            productType = productType,
            productIntroductionImage = productIntroductionImage
        )
}