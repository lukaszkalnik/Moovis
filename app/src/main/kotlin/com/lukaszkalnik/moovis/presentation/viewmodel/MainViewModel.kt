package com.lukaszkalnik.moovis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.lukaszkalnik.moovis.data.model.Configuration
import com.lukaszkalnik.moovis.domain.GetConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MainViewModel(
    private val getConfiguration: GetConfiguration,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    val configuration: LiveData<Configuration> = liveData(viewModelScope.coroutineContext + dispatcher) {
        val configuration = getConfiguration()
        emit(configuration)
    }

    class Factory(
        private val getConfiguration: GetConfiguration
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(
                getConfiguration
            ) as T
    }
}