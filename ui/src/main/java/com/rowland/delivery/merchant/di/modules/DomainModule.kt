package com.rowland.delivery.merchant.di.modules

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that provides all dependencies from the domain package/layer.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

}