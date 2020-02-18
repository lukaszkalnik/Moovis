package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.remote.tmdbApi
import com.lukaszkalnik.moovis.domain.usecase.GetConfiguration
import com.lukaszkalnik.moovis.domain.usecase.getConfigurationFactory

/**
 * Provides use cases to be injected into view models.
 */
object UseCaseInjector {

    val getConfiguration: GetConfiguration get() = getConfigurationFactory(tmdbApi)
}
