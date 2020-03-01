package com.lukaszkalnik.moovis.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lukaszkalnik.moovis.domain.usecase.GetConfiguration
import com.lukaszkalnik.moovis.domain.usecase.GetPopularMovies
import com.lukaszkalnik.moovis.presentation.model.MovieTileItem
import com.lukaszkalnik.moovis.presentation.model.asMovieTileItems
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.data.ImageWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(
    private val getConfiguration: GetConfiguration,
    private val getPopularMovies: GetPopularMovies,
    private val appConfig: AppConfig,
    private val scope: CoroutineScope = MainScope()
) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<List<MovieTileItem>>()

    val movies: LiveData<List<MovieTileItem>> get() = _moviesLiveData

    init {
        scope.launch {
            val tmdbConfiguration = getConfiguration()
            with(tmdbConfiguration.images) {
                appConfig.imagesBaseUrl = secureBaseUrl
                appConfig.posterSizes = posterSizes.map {
                    ImageWidth(it)
                }
            }

            val movies = getPopularMovies(1)
            _moviesLiveData.postValue(movies.asMovieTileItems)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    class Factory(
        private val getConfiguration: GetConfiguration,
        private val getPopularMovies: GetPopularMovies,
        private val appConfig: AppConfig
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
            getConfiguration,
            getPopularMovies,
            appConfig
        ) as T
    }
}
