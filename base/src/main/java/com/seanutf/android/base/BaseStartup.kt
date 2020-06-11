package com.seanutf.android.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.startup.Initializer

class BaseStartup: Initializer<Any> {

    override fun create(context: Context): Any {
        TODO("Not yet implemented")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        TODO("Not yet implemented")
    }
}