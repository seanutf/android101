package com.seanutf.android.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

// Creates MyCustomScope
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCustomScope {
}
