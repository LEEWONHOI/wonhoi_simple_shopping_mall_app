package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.di

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.db.provideDB
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.db.provideTodoDao
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.network.buildOkHttpClient
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.network.provideGsonConverterFactory
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.network.provideProductApiService
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.network.provideProductRetrofit
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.preference.PreferenceManager
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.repository.DefaultProductRepository
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.repository.ProductRepository
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.*
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.DeleteOrderedProductListUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.GetOrderedProductListUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.GetProductItemUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.GetProductListUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain.OrderProductItemUseCase
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.detail.ProductDetailViewModel
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.list.ProductListViewModel
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.main.MainViewModel
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ViewModel
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(),get()) }
    viewModel { (productId : Long) -> ProductDetailViewModel(productId, get(), get()) }

    // CoroutineDispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // UseCases
    factory { GetProductItemUseCase(get()) }
    factory { GetProductListUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }
    factory { GetOrderedProductListUseCase(get()) }
    factory { DeleteOrderedProductListUseCase(get()) }

    // Repositories
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

    // Database
    single { provideDB(androidApplication()) }
    single { provideTodoDao(get()) }

    single { PreferenceManager(androidContext()) }

}