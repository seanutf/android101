package com.seanutf.android.lazyfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.seanutf.android.databinding.ActivityLazyFragment2Binding

class LazyFragmentActivity2 : AppCompatActivity() {
    private lateinit var vb: ActivityLazyFragment2Binding
    private lateinit var pagerAdapter: LazyFragmentTestPagerAdapter
    private val fragmentList = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLazyFragment2Binding.inflate(layoutInflater)
        setContentView(vb.root)
        initFragmentList()
        initView()
    }


    private fun initFragmentList() {
        val lazyFragment1 = LazyFragment1()
        val lazyFragment2 = LazyFragment2()
        fragmentList.add(lazyFragment1)
        fragmentList.add(lazyFragment2)
    }

    private fun initView() {
        pagerAdapter = LazyFragmentTestPagerAdapter(supportFragmentManager)
        vb.viewPager.adapter = pagerAdapter
        //将TabLayout和ViewPager关联起来。
        val mediator = TabLayoutMediator(vb.tabLayout, vb.viewPager
        ) { tab: TabLayout.Tab, position: Int -> setTabTitle(tab, position) }
        mediator.attach()
        val selectTab = vb.tabLayout.getTabAt(0)
        selectTab?.select()
        vb.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun setTabTitle(tab: TabLayout.Tab, position: Int) {
        if (position == 0) {
            tab.text = "LazyFragment1"
        } else if (position == 1) {
            tab.text = "LazyFragment2"
        }
    }

    inner class LazyFragmentTestPagerAdapter(fm: FragmentManager) : FragmentStateAdapter(fm, lifecycle) {
        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = fragmentList[0]
                1 -> fragment = fragmentList[1]
                else -> {
                }
            }
            return fragment!!
        }
    }
}