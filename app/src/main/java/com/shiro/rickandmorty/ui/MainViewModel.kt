package com.shiro.rickandmorty.ui

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.shiro.rickandmorty.R
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.models.Pager
import com.shiro.rickandmorty.domain.use_cases.GetAllCharactersUseCase
import com.shiro.rickandmorty.domain.use_cases.GetCharactersBySearchUseCase
import com.shiro.rickandmorty.helpers.Utils
import com.shiro.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val getCharactersBySearchUseCase: GetCharactersBySearchUseCase
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
        if (isSearch()) getCharactersBySearch()
        else getCharacters()
    }

    fun hasNext(): Boolean = mainUiState.value.hasNext
    fun isLoading(): Boolean = mainUiState.value.isLoading
    fun isSearch(): Boolean = mainUiState.value.searchQuery.isNotEmpty()

    fun setSearchQuery(query: String, submit: Boolean = false) {
        mainUiState.update {
            it.copy(searchQuery = query)
        }
        if (submit) {
            refreshData()
        }
    }

    fun resetQuery() {
        mainUiState.update {
            it.copy(searchQuery = "")
        }
    }

    fun resetErrorMessage() {
        setErrorMessage(0)
    }

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(true)
            getAllCharactersUseCase(mainUiState.value.page)
                .distinctUntilChanged()
                .collect { result ->
                    result.onSuccess { characterResult ->
                        characterResult?.results?.let { setCharacters(it) }
                        characterResult?.info?.let { setPage(it) }
                    }
                    result.onFailure {
                        val error = it as? ApiError
                        setErrorMessage(
                            error?.errorMessage ?: R.string.unknown_error
                        )
                    }
                    setIsLoading(false)
                    setClearList(false)
                }
        }
    }

    fun getCharactersBySearch() {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(true)
            with(mainUiState.value) {
                getCharactersBySearchUseCase(searchQuery, page)
                    .distinctUntilChanged()
                    .collect { result ->
                        result.onSuccess { characterResult ->
                            characterResult?.info?.let {
                                setPage(it)
                                if (!it.prev.isNullOrEmpty())
                                    setClearList(false)
                            }
                            characterResult?.results?.let { setCharacters(it) }
                        }
                        result.onFailure {
                            val error = it as? ApiError
                            setErrorMessage(
                                error?.errorMessage ?: R.string.unknown_error
                            )
                        }
                        setIsLoading(false)
                        setClearList(false)
                    }
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
        val auxCharactersList =
            if (mainUiState.value.clearList) mutableListOf()
            else mainUiState.value.characters as MutableList
        auxCharactersList.addAll(characters)
        mainUiState.update {
            it.copy(characters = auxCharactersList)
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

    private fun setPage(pager: Pager) {
        if (pager.next == null) {
            resetPage()
            setHasNext(false)
        }
        else increasePage()
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