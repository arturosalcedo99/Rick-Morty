package com.example.rickandmorty_arturo.ui

import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty_arturo.R
import com.example.rickandmorty_arturo.databinding.ActivityMainBinding
import com.example.rickandmorty_arturo.ui.base.BaseActivity
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