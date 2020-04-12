@file:JvmName("DialogUtils")

package com.seanutf.cmmonui.notice

import android.app.Activity
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.graphics.Typeface
import androidx.appcompat.app.AlertDialog
import com.seanutf.cmmonui.R


/**
 * 预设的
 * 按钮为"确定"，对应showOneBtnNoTitleAlertDialog()方法
 */
fun Activity.showSureNoTitleDialog(message: String, posListener: OnClickListener?) {
    showOneBtnNoTitleAlertDialog(message, true, getString(R.string.app_sure), posListener)
}

/**
 * 预设的
 * 按钮为"取消"，对应showOneBtnNoTitleAlertDialog()方法
 */
fun Activity.showCancelNoTitleDialog(message: String, negListener: OnClickListener?) {
    showOneBtnNoTitleCancelDialog(message, true, getString(R.string.app_cancel), negListener)
}

/**
 * 预设的
 * 按钮为"确定"，对应showOneBtnTitleAlertDialog()方法
 */
fun Activity.showSureDialog(title: String, message: String, posListener: OnClickListener?) {

    showOneBtnTitleAlertDialog(title, message, true, getString(R.string.app_sure), posListener)

}

/**
 * 预设的
 * 按钮为"取消"，对应showOneBtnTitleAlertDialog()方法
 */
fun Activity.showCancelDialog(title: String, message: String, negListener: OnClickListener?) {

    showOneBtnTitleCancelDialog(title, message, true, getString(R.string.app_cancel), negListener)

}

/**
 * 预设的
 * 按钮为"确定"，"取消"，可返回键取消， 对应showTwoBtnNoTitleAlertDialog()方法
 */
fun Activity.showSelectableNoTitleDialog(message: String, posListener: OnClickListener?, negListener: OnClickListener?) {
    showTwoBtnNoTitleAlertDialog(message, true, getString(R.string.app_sure),
            getString(R.string.app_cancel),
            posListener,
            negListener)

}

/**
 * 预设的
 * 标题为"警告"，按钮为"确定"，"取消"，对应showTwoBtnTitleAlertDialog()方法
 */
fun Activity.showSelectableWarningDialog(message: String, posListener: OnClickListener?,
                                         negListener: OnClickListener?) {
    showTwoBtnTitleAlertDialog(getString(R.string.app_warning),
            message, true, getString(R.string.app_sure),
            getString(R.string.app_cancel),
            posListener,
            negListener)
}

/**
 * 预设的
 * 标题为"提示"，按钮为"确定"，"取消"，对应showTwoBtnTitleAlertDialog()方法
 */
fun Activity.showSelectableNoticeDialog(message: String, posListener: OnClickListener?, negListener: OnClickListener?) {
    showTwoBtnTitleAlertDialog(getString(R.string.app_title),
            message, true, getString(R.string.app_sure),
            getString(R.string.app_cancel),
            posListener,
            negListener)
}

/**
 * 基础的
 * 一个选项，一个提示语
 */

fun Activity.showOneBtnNoTitleAlertDialog(message: String, cancelable: Boolean, posText: String, posListener: OnClickListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(posText, posListener).show()
    }
}

/**
 * 基础的
 * 一个选项，一个提示语
 */

fun Activity.showOneBtnNoTitleCancelDialog(message: String, cancelable: Boolean, negText: String, negListener: OnClickListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setNegativeButton(negText, negListener).show()
    }
}

/**
 * 基础的
 * 一个选项，一条提示语，一个标题
 */

fun Activity.showOneBtnTitleCancelDialog(title: String, message: String, cancelable: Boolean, negText: String, negListener: OnClickListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setTitle(title).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setNegativeButton(negText, negListener).show()
    }
}

/**
 * 基础的
 * 一个选项，一条提示语，一个标题
 */
fun Activity.showOneBtnTitleAlertDialog(title: String, message: String, cancelable: Boolean, posText: String, posListener: OnClickListener?) {
    showOneBtnTitleAlertDialog(title, message, cancelable, posText, posListener, null)
}

/**
 * 基础的
 * 一个选项，一条提示语，一个标题
 */
fun Activity.showOneBtnTitleAlertDialog(title: String, message: String, cancelable: Boolean, posText: String, posListener: OnClickListener?, onDismissListener: DialogInterface.OnDismissListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setTitle(title).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setOnDismissListener(onDismissListener)
        builder.setPositiveButton(posText, posListener).show()
    }
}

/**
 * 基础的
 * 两个选项，一条提示语
 */
fun Activity.showTwoBtnNoTitleAlertDialog(message: String, cancelable: Boolean, posText: String, negText: String, posListener: OnClickListener?, negListener: OnClickListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(posText, posListener)
        builder.setNegativeButton(negText, negListener).show()
    }
}


/**
 * 基础的
 * 两个选项，一条提示语
 * 取消按钮是粗体的
 */
fun Activity.showTwoBtnNoTitleCanCleSoldAlertDialog(message: String, cancelable: Boolean, posText: String, negText: String, posListener: OnClickListener?, negListener: OnClickListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(posText, posListener)
        builder.setNegativeButton(negText, negListener)
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).typeface = Typeface.defaultFromStyle(Typeface.BOLD)

    }
}

/**
 * 基础的
 * 两个选项，一条提示语和一个标题
 */
fun Activity.showTwoBtnTitleAlertDialog(title: String, message: String, cancelable: Boolean, posText: String, negText: String, posListener: OnClickListener?, negListener: OnClickListener?) {
    if (!isFinishing && !isDestroyed) {
        val builder = AlertDialog.Builder(this).setTitle(title).setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(posText, posListener)
        builder.setNegativeButton(negText, negListener).show()
    }
}
