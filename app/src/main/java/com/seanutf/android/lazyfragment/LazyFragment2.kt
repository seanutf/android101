package com.seanutf.android.lazyfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seanutf.android.custom.CustomViewActivity
import com.seanutf.android.databinding.LayoutFragmentLazyTest2Binding
import kotlinx.android.synthetic.main.activity_mvi_test.*

/**
 * @Author zhangyi
 * @Date 2021/1/12 7:51 PM
 * @Desc TODO
 */
class LazyFragment2:Fragment() {
    companion object {
        private const val TAG = "LazyFragmentTest"
    }
    private lateinit var vb:LayoutFragmentLazyTest2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "LazyFragment2 called onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "LazyFragment2 called onCreateView")
        vb = LayoutFragmentLazyTest2Binding.inflate(inflater, container, false)
        return vb.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.tvBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), CustomViewActivity::class.java))
        }
        Log.d(TAG, "LazyFragment2 called onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "LazyFragment2 called onActivityCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "LazyFragment2 called onAttach")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "LazyFragment2 called onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "LazyFragment2 called onResume, and isHidden is: {$isHidden}, and userVisibleHint is{$userVisibleHint}")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "LazyFragment2 called onHiddenChanged,and hidden is:$hidden")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "LazyFragment2 called onSaveInstanceState")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "LazyFragment2 called onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "LazyFragment2 called onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "LazyFragment2 called onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "LazyFragment2 called onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "LazyFragment2 called onDetach")
    }
}