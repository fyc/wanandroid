package luyao.wanandroid.ui.main

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_edu_main.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.FragmentEduMainBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import org.koin.androidx.viewmodel.ext.android.viewModel

class EduMainFragment : BaseVMFragment<FragmentEduMainBinding>(R.layout.fragment_edu_main) {

    private val eduMainViewModel by viewModel<EduMainViewModel>()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val courseAdapter by lazy { CourseAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private val banner by lazy { com.youth.banner.Banner(activity) }

    override fun initView() {
        binding.run {
            viewModel = eduMainViewModel
            adapter = courseAdapter
        }
        initRecycleView()
        initBanner()
    }

    override fun initData() {
        refresh()
    }

    override fun startObserve() {
        eduMainViewModel.apply {
            mBanners.observe(viewLifecycleOwner, Observer { it ->
                it?.let { setBanner(it) }
            })

            uiState.observe(viewLifecycleOwner, Observer {

                it.showSuccess?.let { list ->
                    courseAdapter.run {
                        this.setEnableLoadMore(false)
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) courseAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }

            })
        }
    }

    fun refresh() {
        eduMainViewModel.getEduMainCourseList(true)
    }

    private fun initRecycleView() {
        courseAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = bundleOf(BrowserActivity.URL to courseAdapter.data[position].link)
                NavHostFragment.findNavController(this@EduMainFragment).navigate(R.id.action_tab_to_browser, bundle)
            }
            onItemChildClickListener = this@EduMainFragment.onItemChildClickListener
            if (headerLayoutCount > 0) removeAllHeaderView()
            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, courseRecycleView)
        }
    }

    private fun initBanner() {

        banner.run {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200))
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    Navigation.findNavController(banner).navigate(R.id.action_tab_to_browser, bundleOf(BrowserActivity.URL to bannerUrls[position]))
                }
            }
        }
    }

    private fun setBanner(bannerList: List<BannerData>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        banner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setDelayTime(3000)
        banner.start()
    }

    private fun loadMore() {
        eduMainViewModel.getEduMainCourseList(false)
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
//        when (view.id) {
//            R.id.articleStar -> {
//                if (isLogin) {
//                    courseAdapter.run {
//                        data[position].run {
//                            collect = !collect
//                            mViewModel.collectArticle(id, collect)
//                        }
//                        notifyDataSetChanged()
//                    }
//                } else {
//                    Navigation.findNavController(homeRecycleView).navigate(R.id.action_tab_to_login)
//                }
//            }
//        }
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }
}