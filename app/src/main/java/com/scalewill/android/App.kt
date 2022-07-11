package com.scalewill.android

import android.app.Application
import com.google.firebase.FirebaseApp
import com.scalewill.android.di.initKodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {
    override val kodein = initKodein(this)

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
