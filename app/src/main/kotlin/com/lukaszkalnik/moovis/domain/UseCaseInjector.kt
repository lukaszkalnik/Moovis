package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.TmdbApiService

object UseCaseInjector {

    val getConfiguration: GetConfiguration get() = getConfiguration(TmdbApiService.instance)
}