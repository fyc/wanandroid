package luyao.wanandroid.ui.mine

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_edu_main.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.wanandroid.R
import luyao.wanandroid.address.AddressSelector
import luyao.wanandroid.databinding.FragmentLearningCenterBinding
import luyao.wanandroid.databinding.FragmentMineBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
import org.koin.androidx.viewmodel.ext.android.viewModel


class MineFragment : BaseVMFragment<FragmentMineBinding>(R.layout.fragment_mine) {

    private val mViewModel by viewModel<MineViewModel>()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val mCommonFunAdapter by lazy { CommonFunAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()

    override fun initView() {
        binding.run {
            viewModel = mViewModel
            commonFunAdapter = mCommonFunAdapter
        }
        initTitleBar()
        initRecycleView()
        initBanner()
        initCardView()
    }

    override fun initData() {
        mViewModel.getCommonFunData()
    }

    override fun startObserve() {
        mViewModel.apply {
            commonFunDataList.observe(viewLifecycleOwner, Observer {
                mCommonFunAdapter.run {
                    this.setEnableLoadMore(false)
                    setNewData(it)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            })
            mBanners.observe(viewLifecycleOwner, Observer { it ->
                it?.let { setBanner(it) }
            })

        }
    }


    fun initTitleBar() {
//        var builder: AddressSelector.Builder

//        binding.titleBar.setOnViewClick(object : LearningCenterTitleBar.onViewClick {
//            override fun rightClick(view: View?) {
//
//            }
//
//            override fun leftClick(view: View?) {
//            }
//
//        })
    }

    private fun initRecycleView() {
//        binding.auxiliaryOptionsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.commonFunRecyclerView.layoutManager = gridLayoutManager
    }

    private fun initBanner() {

        binding.banner.run {
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    Navigation.findNavController(banner).navigate(R.id.action_tab_to_browser, bundleOf(BrowserActivity.URL to bannerUrls[position]))
                }
            }
        }
    }
    private fun initTimeTable() {

    }

    private fun initCardView() {

    }

    private fun setBanner(bannerList: List<BannerData>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        binding.banner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setDelayTime(3000)
        binding.banner.start()
    }


    private fun loadMore() {
//        eduMainViewModel.getEduMainCourseList(false)
    }

    override fun onStart() {
        super.onStart()
        binding.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        binding.banner.stopAutoPlay()
    }
}