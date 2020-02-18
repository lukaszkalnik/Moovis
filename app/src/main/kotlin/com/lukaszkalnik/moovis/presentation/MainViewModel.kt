package com.lukaszkalnik.moovis.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lukaszkalnik.moovis.domain.usecase.GetConfiguration
import com.lukaszkalnik.moovis.domain.usecase.GetPopularMovies
import com.lukaszkalnik.moovis.presentation.model.MovieTileItem
import com.lukaszkalnik.moovis.presentation.model.asMovieTileItems
import com.lukaszkalnik.moovis.runtimeconfiguration.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.ImageWidth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getConfiguration: GetConfiguration,
    private val getPopularMovies: GetPopularMovies,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<List<MovieTileItem>>()

    val movies: LiveData<List<MovieTileItem>> get() = _moviesLiveData

    init {
        viewModelScope.launch(dispatcher) {
            val tmdbConfiguration = getConfiguration()
            with(tmdbConfiguration.images) {
                AppConfig.imagesBaseUrl = secureBaseUrl
                AppConfig.posterSizes = posterSizes.map { ImageWidth(it) }
            }

            val movies = getPopularMovies(1)
            _moviesLiveData.postValue(movies.asMovieTileItems)
        }
    }

    class Factory(
        private val getConfiguration: GetConfiguration,
        private val getPopularMovies: GetPopularMovies
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
            getConfiguration,
            getPopularMovies
        ) as T
    }
}
