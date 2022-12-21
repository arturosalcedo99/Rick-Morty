package com.shiro.rickandmorty.ui

import androidx.lifecycle.lifecycleScope
import com.shiro.rickandmorty.R
import com.shiro.rickandmorty.databinding.ActivityMainBinding
import com.shiro.rickandmorty.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    override fun setUpViews() {
        initObservers()
    }

    private fun initObservers() = lifecycleScope.launch {

    }
}