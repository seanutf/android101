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
import com.seanutf.android.databinding.LayoutFragmentLazyTest1Binding

/**
 * @Author zhangyi
 * @Date 2021/1/12 7:50 PM
 * @Desc TODO
 */
class LazyFragment1:Fragment() {

    companion object {
        private const val TAG = "LazyFragmentTest"
    }
    private lateinit var vb:LayoutFragmentLazyTest1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "LazyFragment1 called onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "LazyFragment1 called onCreateView")
        vb = LayoutFragmentLazyTest1Binding.inflate(inflater, container, false)
        return vb.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.tvBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), CustomViewActivity::class.java))
        }
        Log.d(TAG, "LazyFragment1 called onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "LazyFragment1 called onActivityCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "LazyFragment1 called onAttach")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "LazyFragment1 called onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "LazyFragment1 called onResume, and isHidden is: {$isHidden}, and userVisibleHint is{$userVisibleHint}")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "LazyFragment1 called onHiddenChanged,and hidden is:$hidden")
    }

    override fun getUserVisibleHint(): Boolean {
        return super.getUserVisibleHint()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "LazyFragment1 called onSaveInstanceState")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "LazyFragment1 called onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "LazyFragment1 called onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "LazyFragment1 called onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "LazyFragment1 called onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "LazyFragment1 called onDetach")
    }
}