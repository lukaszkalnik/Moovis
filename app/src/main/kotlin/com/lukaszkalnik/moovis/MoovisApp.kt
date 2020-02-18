package com.lukaszkalnik.moovis

import android.app.Application
import android.content.res.Resources
import com.lukaszkalnik.moovis.runtimeconfiguration.RuntimeConfiguration

class MoovisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RuntimeConfiguration.locale = Resources.getSystem().configuration.locales[0].toLanguageTag()
    }
}