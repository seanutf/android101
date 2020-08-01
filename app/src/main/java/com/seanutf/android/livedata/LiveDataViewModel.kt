package com.seanutf.android.livedata

import android.text.TextUtils
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {

    private var mProducts: LiveData<List<String>>? = null
    private val manager: LiveDataManager = LiveDataManager()

    fun startCount() {

        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered

        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered
        mProducts = Transformations.switchMap<CharSequence?, List<String>>(
                manager.getLiveData("QUERY", null),
                (label@ Function<CharSequence?, LiveData<List<String>>> { query: CharSequence? ->
                    if (TextUtils.isEmpty(query)) {
                        return@label manager.getProducts()
                    }
                    manager.searchProducts("*$query*")
                } as Function<CharSequence?, LiveData<List<String>>>))
    }

    fun testTry() {
        val result = kotlin.runCatching { doSomeThings() }.isFailure
        val result1 = kotlin.runCatching { doSomeThings() }.isSuccess

        val result3 = kotlin.runCatching { doSomeThings() }.getOrThrow()
        val result4 = kotlin.runCatching { doSomeThings() }.getOrElse { doSomeThings() }
    }

    @Throws
    fun doSomeThings(): Exception {
        return NullPointerException()
    }
}