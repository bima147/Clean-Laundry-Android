package com.bimaprakoso.cleanlaundrybootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimaprakoso.cleanlaundrybootcamp.domain.HomeRepository
import com.bimaprakoso.cleanlaundrybootcamp.domain.model.Home
import com.bimaprakoso.cleanlaundrybootcamp.domain.model.User
import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.BaseError
import com.bimaprakoso.cleanlaundrybootcamp.utils.Initiate
import com.bimaprakoso.cleanlaundrybootcamp.utils.Loading
import com.bimaprakoso.cleanlaundrybootcamp.utils.Success
import com.bimaprakoso.cleanlaundrybootcamp.utils.Error
import com.bimaprakoso.cleanlaundrybootcamp.utils.NetworkState
import com.bimaprakoso.cleanlaundrybootcamp.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: HomeRepository): ViewModel() {
    private val _home = MutableStateFlow<UIState<Home>>(Initiate())
    val home: StateFlow<UIState<Home>> = _home

    fun getData(token: String){
        viewModelScope.launch(Dispatchers.Main) {
            _home.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.getDataHome(token = token)
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _home.value = Loading(false)
                    _home.value = Success(data = state.data)
                }
                is NetworkState.Error -> {
                    _home.value = Loading(false)
                    _home.value = Error((state.error as BaseError).error)
                }
            }
        }
    }
}