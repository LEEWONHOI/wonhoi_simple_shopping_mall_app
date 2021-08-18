package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.db

import android.content.Context
import androidx.room.Room

internal fun provideDB(context: Context) : ProductDatabase =
    Room.databaseBuilder(context, ProductDatabase::class.java, ProductDatabase.DB_NAME).build()

internal fun provideTodoDao(database: ProductDatabase) = database.productDao()