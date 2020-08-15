package com.seanutf.android.livedata

import android.text.TextUtils
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.seanutf.android.base.media.data.MediaItem
import com.seanutf.android.base.utils.nonNullOrNull

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
                (Function<CharSequence?, LiveData<List<String>>> { query: CharSequence? ->
                    if (TextUtils.isEmpty(query)) {
                        manager.getProducts()
                    }
                    manager.searchProducts("*$query*")
                } as Function<CharSequence?, LiveData<List<String>>>))
    }

    fun testTry() {

        require(true)
        val mediaItem: MediaItem?
        mediaItem = MediaItem()
        val result = kotlin.runCatching { doSomeThings() }.isFailure
        val result1 = kotlin.runCatching { doSomeThings() }.isSuccess
        nonNullOrNull(mediaItem,
                {
                    it.isFirstSelect
                }, { val cc = "fff" })
        val result3 = kotlin.runCatching { doSomeThings() }.getOrThrow()
        val result4 = kotlin.runCatching { doSomeThings() }.getOrElse { doSomeThings() }
    }

    @Throws
    fun doSomeThings(): Exception {
        return NullPointerException()
    }
}