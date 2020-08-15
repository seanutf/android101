package com.seanutf.android.base.utils


fun <T, R> nonNullOrNull(value: T?, notNullBlock: (T) -> R, isNullBlock: (() -> Unit)? = null) {

    if (value != null) {
        notNullBlock(value)
    } else {
        if (isNullBlock != null) {
            isNullBlock()
        }
    }
}