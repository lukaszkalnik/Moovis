package com.lukaszkalnik.moovis.runtimeconfiguration

object Configuration {

    /**
     * Current device locale for selecting the results language.
     * Should be in form e.g. en-US.
     */
    var language: String? = null

    /**
     * Current device country for filtering country dependent data like release dates.
     * Should be in form e.g. `US`.
     */
    var region: String? = null
}
