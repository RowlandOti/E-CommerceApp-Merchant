package com.rowland.delivery.features.dash.di.scope.order;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Rowland on 5/9/2018.
 */


@Scope
@Documented
@Retention(RUNTIME)
public @interface Order {}