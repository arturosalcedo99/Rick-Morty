package com.shiro.rickandmorty.ui

import android.os.Bundle
import com.shiro.rickandmorty.data.source.local.preferences.AppPreferences
import com.shiro.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val preferences: AppPreferences
) : BaseViewModel() {

    override fun initData(data: Bundle) {

    }
}