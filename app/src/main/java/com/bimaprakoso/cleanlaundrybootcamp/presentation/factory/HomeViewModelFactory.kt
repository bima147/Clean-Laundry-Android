package com.bimaprakoso.cleanlaundrybootcamp.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bimaprakoso.cleanlaundrybootcamp.domain.HomeRepository
import com.bimaprakoso.cleanlaundrybootcamp.presentation.viewmodel.HomeViewModel

class HomeViewModelFactory(private val repository: HomeRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel not Found")
        }
    }
}