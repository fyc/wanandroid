package luyao.wanandroid.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import luyao.mvvm.core.base.BaseFragment
import luyao.wanandroid.R
import luyao.wanandroid.ui.found.FoundFragment
import luyao.wanandroid.ui.learning.LearningCenterFragment
import luyao.wanandroid.ui.main.EduMainFragment
import luyao.wanandroid.ui.main.MainFragment
import luyao.wanandroid.ui.mine.MineFragment
import luyao.wanandroid.ui.profile.ProfileFragment
import luyao.wanandroid.ui.project.BlogFragment
import luyao.wanandroid.ui.project.ProjectFragment
import luyao.wanandroid.ui.search.SearchFragment

/**
 * 这是首页 Tab
 */
class TabFragment : BaseFragment() {

    private val fragmentList = arrayListOf<Fragment>()
    private val mainFragment by lazy { EduMainFragment() }
    private val blogFragment by lazy { LearningCenterFragment() }
//    private val searchFragment by lazy { SearchFragment() }
    private val projectFragment by lazy { FoundFragment() }
    private val profileFragment by lazy { MineFragment() }

    init {
        fragmentList.run {
            add(mainFragment)
            add(blogFragment)
//            add(searchFragment)
            add(projectFragment)
            add(profileFragment)
        }
    }

    override fun getLayoutResId() = R.layout.activity_bottom_navigation

    override fun initView() {
        initViewPager()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelected)
    }

    override fun initData() {
    }


    private val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                switchFragment(0)
            }
            R.id.blog -> {
                switchFragment(1)
            }
//            R.id.search -> {
//                switchFragment(2)
//            }
            R.id.project -> {
                switchFragment(2)
            }
            R.id.profile -> {
                switchFragment(3)
            }
        }
        true
    }

    private fun switchFragment(position: Int): Boolean {
//        mainViewpager.currentItem = position
        mainViewpager.setCurrentItem(position, false)
        return true
    }

    private fun initViewPager() {
        mainViewpager.isUserInputEnabled = false
        mainViewpager.offscreenPageLimit = 2
        mainViewpager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = fragmentList.size
        }
    }


}
