package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.TmdbApiService

/**
 * Provides use cases to be injected into view models.
 */
object UseCaseInjector {

    val getConfiguration: GetConfiguration get() = getConfiguration(TmdbApiService.instance)
}