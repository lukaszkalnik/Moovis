package com.lukaszkalnik.moovis

import android.app.Application
import android.content.res.Resources
import android.os.Build
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig
import java.util.Locale

class MoovisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        with(getLocale()) {
            AppConfig.instance.language = toLanguageTag()
            AppConfig.instance.region = country
        }
    }
}

private fun getLocale(): Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Resources.getSystem().configuration.locales[0]
} else {
    Resources.getSystem().configuration.locale
}
