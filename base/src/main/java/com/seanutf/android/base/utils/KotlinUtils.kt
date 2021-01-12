package com.seanutf.android.base.utils


/**
 *  @author sean
 *  功能描述: 使用Kotlin创建的一些工具类
 *  时 间： 2020/8/17 12:26 PM
 */

/**
 * 判断对象是否为null，为null执行一些业务操作
 * 不为null执行其他业务操作
 * 该方法本意是取代
 * if(object != null){
 * } else {
 * }
 * 模版代码
 * 注意：isNullBlock不能为null
 * 如果需要为null
 * 要注明noinline
 * */
inline fun <T, R> isNullObject(value: T?, notNullBlock: (T) -> R, isNullBlock: (() -> Unit) = {}) {

    if (value != null) {
        notNullBlock(value)
    } else {
        isNullBlock()
    }
}