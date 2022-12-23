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
    val characters: List<Character> = mutableListOf(),
    val clearList: Boolean = false,
    val hasNext: Boolean = true
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

    fun refreshData() {
        resetPage()
        increasePage()
        setClearList(true)
        getCharacters()
        setClearList(false)
    }

    fun hasNext(): Boolean = mainUiState.value.hasNext
    fun isLoading(): Boolean = mainUiState.value.isLoading

    fun getCharacters() {
        viewModelScope.launch {
            setIsLoading(true)
            getAllCharactersUseCase(mainUiState.value.page)
                .collect { result ->
                    result.onSuccess { characterResult ->
                        val auxCharactersList =
                            if (mainUiState.value.clearList) mutableListOf()
                            else mainUiState.value.characters as MutableList
                        characterResult?.results?.let {
                            auxCharactersList.addAll(characterResult.results)
                            setCharacters(auxCharactersList)
                        }
                        characterResult?.info?.let { pager ->
                            if (pager.next == null) {
                                resetPage()
                                setHasNext(false)
                            }
                            else increasePage()
                        }
                    }
                    result.onFailure {
                        val error = it as? ApiError
                        setErrorMessage(
                            error?.errorMessage ?: R.string.unknown_error
                        )
                    }
                    setIsLoading(false)
                }
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        mainUiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun setErrorMessage(errorMessage: Int) {
        mainUiState.update {
            it.copy(errorMessage = errorMessage)
        }
    }

    private fun setCharacters(characters: List<Character>) {
        mainUiState.update {
            it.copy(characters = characters)
        }
    }

    private fun increasePage() {
        mainUiState.update {
            it.copy(page = it.page + 1)
        }
    }

    private fun resetPage() {
        mainUiState.update {
            it.copy(page = 0)
        }
    }

    private fun setClearList(clearList: Boolean) {
        mainUiState.update {
            it.copy(clearList = clearList)
        }
    }

    private fun setHasNext(hasNext: Boolean) {
        mainUiState.update {
            it.copy(hasNext = hasNext)
        }
    }
}