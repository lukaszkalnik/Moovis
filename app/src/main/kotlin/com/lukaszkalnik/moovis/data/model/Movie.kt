package com.lukaszkalnik.moovis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("poster_path")
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("original_language")
    val originalLanguage: String,
    val title: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val popularity: Float,
    @SerialName("vote_count")
    val voteCount: Int,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Float
)
