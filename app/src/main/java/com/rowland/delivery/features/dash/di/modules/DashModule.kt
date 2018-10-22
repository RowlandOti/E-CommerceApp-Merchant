package com.rowland.delivery.features.dash.di.modules

import com.rowland.delivery.merchant.application.di.modules.ContextModule
import com.rowland.delivery.services.firebase.modules.FirebaseModule
import com.rowland.delivery.services.session.di.modules.SessionModule
import dagger.Module

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = arrayOf(FirebaseModule::class, ContextModule::class, SessionModule::class, ViewModelFactoryModule::class, RepositoryModule::class))
class DashModule {

}
