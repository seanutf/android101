package com.seanutf.android.lazyfragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.seanutf.android.databinding.ActivityLazyFragmentBinding
import com.seanutf.cmmonui.arch.BaseActivity

/**
 * @Author Sean
 * @Date 2021/1/12 7:35 PM
 * @Desc Fragment懒加载处理
 */
class LazyFragmentActivity :BaseActivity(){
    private lateinit var vb:ActivityLazyFragmentBinding
    private lateinit var pagerAdapter: LazyFragmentTestPagerAdapter
    private val fragmentList = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLazyFragmentBinding.inflate(layoutInflater)
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
        vb.tabLayout.setupWithViewPager(vb.viewPager)
        val selectTab = vb.tabLayout.getTabAt(0)
        selectTab?.select()
        vb.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    inner class LazyFragmentTestPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        var currentFragment: Fragment? = null
        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = fragmentList[0]
                1 -> fragment = fragmentList[1]
                else -> {
                }
            }
            return fragment!!
        }

        override fun getCount(): Int {
            return 2
        }

        //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
        override fun getPageTitle(position: Int): CharSequence? {
            var title = ""
            when (position) {
                0 -> title = "LazyFragment1"
                1 -> title = "LazyFragment2"
                else -> {
                }
            }
            return title
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            super.setPrimaryItem(container, position, `object`)
            if (`object` is Fragment) {
                currentFragment = `object`
            }
        }
    }
}