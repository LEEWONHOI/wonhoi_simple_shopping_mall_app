package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.db.dao.ProductDao
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.utility.DateConverter


@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ProductDatabase : RoomDatabase() {

    companion object{
        const val DB_NAME = "ProductDataBase.db"
    }

    abstract fun productDao() : ProductDao

}