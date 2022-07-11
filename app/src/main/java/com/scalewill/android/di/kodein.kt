package com.scalewill.android.di

import android.app.Application
import com.scalewill.android.di.modules.appModule
import com.scalewill.android.di.modules.dbModule
import com.scalewill.android.di.modules.netModule
import com.scalewill.android.di.modules.repoModule
import com.scalewill.android.di.modules.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.android.x.androidXModule

fun initKodein(app: Application) =
    Kodein.lazy {
        import(androidXModule(app))
        import(dbModule)
        import(appModule)
        import(viewModelModule)
        import(netModule)
        import(repoModule)
    }
