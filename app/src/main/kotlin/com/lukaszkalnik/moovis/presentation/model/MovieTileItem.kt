package com.lukaszkalnik.moovis.presentation.model

import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.data.findCeiling

data class MovieTileItem(
    val id: Int,
    val title: String,
    val imageUri: String,
    val description: String,
    val popularity: Float
)

fun toMovieTileItems(moviesPage: MoviesPage, appConfig: AppConfig): List<MovieTileItem> =
    moviesPage.results
        .sortedByDescending {
            it.popularity
        }.map {
            val imagePath = appConfig.imagesBaseUrl +
                    appConfig.posterSizes.findCeiling(500) +
                    it.posterPath

            MovieTileItem(it.id, it.title, imagePath, it.overview, it.popularity)
        }
