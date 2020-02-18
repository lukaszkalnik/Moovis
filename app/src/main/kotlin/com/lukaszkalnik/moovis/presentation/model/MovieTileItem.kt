package com.lukaszkalnik.moovis.presentation.model

import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.runtimeconfiguration.AppConfig
import com.lukaszkalnik.moovis.runtimeconfiguration.findCeiling

data class MovieTileItem(
    val id: Int,
    val title: String,
    val imageUri: String,
    val description: String
)

val MoviesPage.asMovieTileItems: List<MovieTileItem>
    get() = results.map {
        val imagePath = AppConfig.imagesBaseUrl +
                AppConfig.posterSizes.findCeiling(500) +
                it.posterPath

        MovieTileItem(it.id, it.title, imagePath, it.overview)
    }
