package com.lukaszkalnik.moovis.presentation.model

import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.runtimeconfiguration.data.DefaultAppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.data.findCeiling

data class MovieTileItem(
    val id: Int,
    val title: String,
    val imageUri: String,
    val description: String,
    val popularity: Float
)

val MoviesPage.asMovieTileItems: List<MovieTileItem>
    get() = results
        .sortedByDescending {
            it.popularity
        }.map {
            val imagePath = DefaultAppConfig.imagesBaseUrl +
                    DefaultAppConfig.posterSizes.findCeiling(500) +
                    it.posterPath

            MovieTileItem(it.id, it.title, imagePath, it.overview, it.popularity)
        }
