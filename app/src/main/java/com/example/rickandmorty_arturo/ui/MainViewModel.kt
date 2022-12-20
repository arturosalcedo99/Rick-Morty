package com.example.rickandmorty_arturo.ui

import android.os.Bundle
import com.example.rickandmorty_arturo.data.source.local.preferences.AppPreferences
import com.example.rickandmorty_arturo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val preferences: AppPreferences
) : BaseViewModel() {

    override fun initData(data: Bundle) {

    }
}