package com.lukaszkalnik.moovis.domain.usecase

import arrow.core.Either
import com.lukaszkalnik.moovis.data.model.MoviesPage
import com.lukaszkalnik.moovis.data.remote.TmdbApi

/**
 * Get popular movies page for the given page number.
 */
typealias GetPopularMovies = suspend (Int) -> Either<Exception, MoviesPage>

fun getPopularMoviesFactory(
    tmdbApi: TmdbApi,
    language: String,
    region: String
): GetPopularMovies = { page: Int -> tmdbApi.getPopularMovies(language, page, region) }
