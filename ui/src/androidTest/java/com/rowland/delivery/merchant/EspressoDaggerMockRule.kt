package com.rowland.delivery.merchant

import androidx.test.platform.app.InstrumentationRegistry
import com.rowland.delivery.merchant.di.components.ApplicationComponent
import com.rowland.delivery.merchant.di.modules.ContextModule
import com.rowland.delivery.merchant.features.MerchantApplication
import it.cosenonjaviste.daggermock.DaggerMock

fun espressoDaggerMockRule() = DaggerMock.rule<ApplicationComponent>(ContextModule(app)) {
    set { component -> app.applicationComponent = component }
}

val app: MerchantApplication get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MerchantApplication