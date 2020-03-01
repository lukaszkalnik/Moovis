package com.lukaszkalnik.moovis

import android.app.Application
import android.content.res.Resources
import com.lukaszkalnik.moovis.runtimeconfiguration.data.DefaultAppConfig

class MoovisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        with(Resources.getSystem().configuration.locales[0]) {
            DefaultAppConfig.language = toLanguageTag()
            DefaultAppConfig.region = country
        }
    }
}
