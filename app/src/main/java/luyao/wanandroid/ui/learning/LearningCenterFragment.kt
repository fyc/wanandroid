package luyao.wanandroid.ui.learning

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
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LearningCenterFragment : BaseVMFragment<FragmentLearningCenterBinding>(R.layout.fragment_learning_center) {

    private val mViewModel by viewModel<LearningCenterViewModel>()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val mAuxiliaryAdapter by lazy { AuxiliaryAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()

    override fun initView() {
        binding.run {
            viewModel = mViewModel
            auxiliaryAdapter = mAuxiliaryAdapter
        }
        initTitleBar()
        initRecycleView()
        initBanner()
        initCardView()
    }

    override fun initData() {
        mViewModel.getAuxiliaryData()
        mViewModel.getTableCourseData()
    }

    override fun startObserve() {
        mViewModel.apply {
            auxiliaryDataList.observe(viewLifecycleOwner, Observer {
                mAuxiliaryAdapter.run {
                    this.setEnableLoadMore(false)
                    setNewData(it)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            })
            mBanners.observe(viewLifecycleOwner, Observer { it ->
                it?.let { setBanner(it) }
            })

            courseList.observe(viewLifecycleOwner, Observer {
                binding.timeTable.run {
                    //获取开学时间
                    this.loadData(it)
                }
            })

        }
    }


    fun initTitleBar() {
        var builder: AddressSelector.Builder

        binding.titleBar.setOnViewClick(object : LearningCenterTitleBar.onViewClick {
            override fun rightClick(view: View?) {

            }

            override fun leftClick(view: View?) {
            }

        })
    }

    private fun initRecycleView() {
//        binding.auxiliaryOptionsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val gridLayoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.auxiliaryOptionsRecyclerView.layoutManager = gridLayoutManager
    }

    private fun initBanner() {

        binding.learningBanner.run {
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

        binding.courseNoticeCardView.setNoticeStatu()
        binding.findTeacherCardView.setFindStatu()

    }

    private fun setBanner(bannerList: List<LearningCenterBannerData>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        binding.learningBanner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setDelayTime(3000)
        binding.learningBanner.start()
    }


    private fun loadMore() {
//        eduMainViewModel.getEduMainCourseList(false)
    }

    override fun onStart() {
        super.onStart()
        binding.learningBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        binding.learningBanner.stopAutoPlay()
    }
}