package com.lukaszkalnik.moovis.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lukaszkalnik.moovis.data.model.Configuration
import com.lukaszkalnik.moovis.domain.usecase.GetConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getConfiguration: GetConfiguration,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _configurationLiveData = MutableLiveData<Configuration>()

    val configuration: LiveData<Configuration> get() = _configurationLiveData

    init {
        viewModelScope.launch(dispatcher) {
            val configuration = getConfiguration()
            _configurationLiveData.postValue(configuration)
        }
    }

    class Factory(
        private val getConfiguration: GetConfiguration
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
            getConfiguration
        ) as T
    }
}
