package com.scalewill.android.di.modules

import com.scalewill.android.Router
import com.scalewill.android.login.LoginMapper
import com.scalewill.android.model.network.errors.ErrorHandler
import com.scalewill.android.util.StringResource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val appModule = Kodein.Module(name = "appModule") {
    bind<StringResource>() with singleton { StringResource(instance()) }
    bind<ErrorHandler>() with singleton { ErrorHandler(instance(), instance(), instance()) }
    bind<Router>() with singleton { Router(instance()) }
    bind<LoginMapper>() with singleton { LoginMapper() }
}
