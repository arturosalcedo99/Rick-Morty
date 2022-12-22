package com.shiro.rickandmorty.ui

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.shiro.rickandmorty.R
import com.shiro.rickandmorty.data.source.local.preferences.AppPreferences
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.use_cases.GetAllCharactersUseCase
import com.shiro.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    var page: Int = 1,
    var searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: Int = 0,
    val characters: List<Character> = mutableListOf()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : BaseViewModel() {

    val mainUiState by lazy {
        MutableStateFlow(MainUiState())
    }

    override fun initData(data: Bundle) {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            mainUiState.value = mainUiState.value.copy(
                isLoading = true
            )
            getAllCharactersUseCase(mainUiState.value.page)
                .collect { result ->
                    result.onSuccess { characterResult ->
                        val auxCharactersList = mainUiState.value.characters as MutableList
                        val currentPage = mainUiState.value.page
                        characterResult?.results?.let {
                            auxCharactersList.addAll(characterResult.results)
                            /*mainUiState.update {
                                it.copy(
                                    page = currentPage + 1,
                                    characters = auxCharactersList
                                )
                            }*/
                        }
                        characterResult?.info?.let {
                            if (it.next == null)
                                mainUiState.value = mainUiState.value.copy(
                                    page = 0
                                )
                        }
                    }
                    result.onFailure {
                        val error = it as? ApiError
                        mainUiState.value = mainUiState.value.copy(
                            errorMessage = error?.errorMessage ?: R.string.unknown_error
                        )
                    }
                    mainUiState.value = mainUiState.value.copy(
                        isLoading = false
                    )
                }
        }
    }
}