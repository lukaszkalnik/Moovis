package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.tmdbApi

/**
 * Provides use cases to be injected into view models.
 */
object UseCaseInjector {

    val getConfiguration: GetConfiguration get() = getConfigurationFactory(tmdbApi)
}
