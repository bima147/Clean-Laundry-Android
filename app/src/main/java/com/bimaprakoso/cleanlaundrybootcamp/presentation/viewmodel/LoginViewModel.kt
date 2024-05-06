package com.bimaprakoso.cleanlaundrybootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimaprakoso.cleanlaundrybootcamp.domain.LoginRepository
import com.bimaprakoso.cleanlaundrybootcamp.domain.model.User
import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.BaseError
import com.bimaprakoso.cleanlaundrybootcamp.utils.NetworkState
import com.bimaprakoso.cleanlaundrybootcamp.utils.Success
import com.bimaprakoso.cleanlaundrybootcamp.utils.Error
import com.bimaprakoso.cleanlaundrybootcamp.utils.Initiate
import com.bimaprakoso.cleanlaundrybootcamp.utils.Loading
import com.bimaprakoso.cleanlaundrybootcamp.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
): ViewModel(){
    private val _login = MutableStateFlow<UIState<User>>(Initiate())
    val login: StateFlow<UIState<User>> = _login

    fun postLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _login.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.postLogin(LoginRequest(username,password))
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _login.value = Loading(false)
                    _login.value = Success(data = state.data)
                }
                is NetworkState.Error -> {
                    _login.value = Loading(false)
                    _login.value = Error((state.error as BaseError).error)
                }
            }
        }
    }


}