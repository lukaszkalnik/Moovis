package com.lukaszkalnik.moovis.domain.usecase

import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.data.remote.TmdbApi

typealias GetPopularMovies = suspend (Int) -> MoviesPage

fun getPopularMoviesFactory(
    tmdbApi: TmdbApi,
    language: String,
    region: String
): GetPopularMovies = { page: Int -> tmdbApi.getPopularMovies(language, page, region) }
