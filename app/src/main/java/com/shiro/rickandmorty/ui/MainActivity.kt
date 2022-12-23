package com.shiro.rickandmorty.ui

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shiro.rickandmorty.R
import com.shiro.rickandmorty.databinding.ActivityMainBinding
import com.shiro.rickandmorty.helpers.Constants
import com.shiro.rickandmorty.helpers.Utils
import com.shiro.rickandmorty.ui.adapters.CharactersAdapter
import com.shiro.rickandmorty.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    private lateinit var charactersAdapter: CharactersAdapter

    override fun setUpViews() {
        initAdapter()
        initListeners()
        initObservers()
    }

    private fun initAdapter() {
        charactersAdapter =
            CharactersAdapter(this) {  }
        binding.recyclerCharacters.adapter = charactersAdapter
    }

    private fun initListeners() {
        binding.refreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.refreshData()
            }
        }
        binding.recyclerCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = binding.recyclerCharacters.layoutManager as? GridLayoutManager
                layoutManager?.let {
                    if (it.findLastCompletelyVisibleItemPosition() >= charactersAdapter.itemCount - 4
                        && viewModel.hasNext() && !viewModel.isLoading()) {
                        viewModel.getCharacters()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun initObservers() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState
                    .map { it.isLoading }
                    .collect {
                        if (it) showProgressContainer()
                        else hideProgressContainer()
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState
                    .map { it.errorMessage }
                    .collect {
                        if (it != 0) {
                            val bundle = Bundle()
                            bundle.putString(Constants.DIALOG_KEY_TITLE, getString(R.string.error_title))
                            bundle.putString(Constants.DIALOG_KEY_DESCRIPTION, getString(it))
                            bundle.putString(Constants.DIALOG_KEY_ACCEPT, getString(R.string.accept))
                            Utils.showDoubleDialog(
                                this@MainActivity,
                                bundle
                            )
                        }
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState
                    .map { it.characters }
                    .collect {
                        charactersAdapter.submitList(it.toMutableList())
                    }
            }
        }

    }
}