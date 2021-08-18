package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.R
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.databinding.ActivityMainBinding
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseActivity
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseFragment
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.list.ProductListFragment
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        bottomNav.setOnNavigationItemSelectedListener(this@MainActivity)
        showFragment(ProductListFragment(), ProductListFragment.TAG)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_products_list -> {
                showFragment(ProductListFragment(), ProductListFragment.TAG)
                true
            }
            R.id.menu_profile -> {
                showFragment(ProfileFragment(),ProfileFragment.TAG)
                true
            }
            else -> false
        }
    }

    private fun showFragment(fragment : Fragment, tag : String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        // When existing fragments already exists (regardless of tag) -> hide
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }
        // When there is a fragment matching the tag value -> show
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commit()
        // When there is no fragment matching the tag value (first start time) -> add
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss() // When switching screen
        }
    }

    override fun observeData() = viewModel.mainStateLiveData.observe(this){ mainState ->
        when(mainState) {
            is MainState.RefreshOrderList -> {
                binding.bottomNav.selectedItemId = R.id.menu_profile
                val fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)
                // fragment BaseFragment Type Casting -> Fetch Data -> refresh
                (fragment as? BaseFragment<*, *>)?.viewModel?.fetchData()
            }
        }
    }
}