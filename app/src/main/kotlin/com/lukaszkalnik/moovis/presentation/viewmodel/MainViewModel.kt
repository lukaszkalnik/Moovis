package com.lukaszkalnik.moovis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.lukaszkalnik.moovis.data.model.Configuration
import com.lukaszkalnik.moovis.domain.GetConfiguration

class MainViewModel(
    private val getConfiguration: GetConfiguration
) : ViewModel() {

    val configuration: LiveData<Configuration> = liveData {
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