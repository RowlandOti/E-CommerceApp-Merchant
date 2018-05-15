package com.rowland.delivery.features.dash.di.scope.category;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Rowland on 5/7/2018.
 */


@Scope
@Documented
@Retention(RUNTIME)
public @interface CategoryScope {}
