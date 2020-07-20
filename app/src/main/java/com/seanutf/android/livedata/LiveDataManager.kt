package com.seanutf.android.livedata

import androidx.lifecycle.MediatorLiveData

class LiveDataManager {

    private var mObservableProducts: MediatorLiveData<List<String>>? = null
    private var mObservableChar: MediatorLiveData<CharSequence>? = null

    init {
        mObservableProducts = MediatorLiveData<List<String>>()
        mObservableChar = MediatorLiveData<CharSequence>()

//        mObservableProducts!!.addSource(mDatabase.productDao().loadAllProducts(),
//                Observer<List<String>> { productEntities: List<String>? ->
//                    if (mDatabase.getDatabaseCreated().getValue() != null) {
//                        mObservableProducts!!.postValue(productEntities)
//                    }
//                })
    }

    fun getLiveData(tt: CharSequence, list: List<String>?): MediatorLiveData<CharSequence> {
        mObservableChar?.postValue(tt)
        return mObservableChar!!
    }

    fun getProducts(): MediatorLiveData<List<String>>? {
        return mObservableProducts
    }

    fun searchProducts(tt: String?): MediatorLiveData<List<String>>? {
        return mObservableProducts
    }
}