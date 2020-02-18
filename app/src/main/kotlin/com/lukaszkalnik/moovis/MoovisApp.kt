package com.lukaszkalnik.moovis

import android.app.Application
import android.content.res.Resources
import com.lukaszkalnik.moovis.runtimeconfiguration.Configuration

class MoovisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        with(Resources.getSystem().configuration.locales[0]) {
            Configuration.language = toLanguageTag()
            Configuration.region = country
        }
    }
}