package com.lukaszkalnik.moovis.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.domain.usecase.GetConfiguration
import com.lukaszkalnik.moovis.domain.usecase.GetPopularMovies
import com.lukaszkalnik.moovis.presentation.model.MovieTileItem
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.data.ImageWidth
import com.lukaszkalnik.moovis.util.suspendFlatMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(
    private val getConfiguration: GetConfiguration,
    private val getPopularMovies: GetPopularMovies,
    private val appConfig: AppConfig,
    private val toMovieTileItems: (MoviesPage, AppConfig) -> List<MovieTileItem>,
    private val coroutineScope: CoroutineScope = MainScope()
) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<List<MovieTileItem>>()

    val movies: LiveData<List<MovieTileItem>> get() = _moviesLiveData

    init {
        coroutineScope.launch {
            getConfiguration()
                .suspendFlatMap { configuration ->
                    with(configuration.images) {
                        appConfig.imagesBaseUrl = secureBaseUrl
                        appConfig.posterSizes = posterSizes.map { ImageWidth(it) }
                    }
                    getPopularMovies(1)
                }.map { moviesPage ->
                    toMovieTileItems(moviesPage, appConfig)
                }.fold(
                    ifLeft = { println("MainViewModel get config/movies error: $it") },
                    ifRight = { movieTileItems -> _moviesLiveData.postValue(movieTileItems) }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
