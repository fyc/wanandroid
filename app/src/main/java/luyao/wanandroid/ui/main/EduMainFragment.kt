package luyao.wanandroid.ui.main

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_edu_main.*
import kotlinx.coroutines.GlobalScope
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.address.AddressSelector
import luyao.wanandroid.address.AddressSelectorKt
import luyao.wanandroid.databinding.FragmentEduMainBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.main.EduTitleBar.onViewClick
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import org.koin.androidx.viewmodel.ext.android.viewModel


class EduMainFragment : BaseVMFragment<FragmentEduMainBinding>(R.layout.fragment_edu_main) {

    private val eduMainViewModel by viewModel<EduMainViewModel>()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
//    private var mDoorToDoorSpinnerAdapter: ArrayAdapter<String>? = null
    private val mDoorToDoorAdapter by lazy { DoorToDoorAdapter() }
    private val mCourseAdapter by lazy { CourseAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
//    private val banner by lazy { com.youth.banner.Banner(activity) }

    override fun initView() {
        binding.run {
            viewModel = eduMainViewModel
            doorToDoorAdapter = mDoorToDoorAdapter
            courseAdapter = mCourseAdapter
        }
        initTitleBar()
        initDoorToDoorSpinner()
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
                    mCourseAdapter.run {
                        this.setEnableLoadMore(false)
                        if (it.isRefresh) replaceData(list)
                        else addData(list)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                    mDoorToDoorAdapter.run {
                        this.setEnableLoadMore(false)
                        if (it.isRefresh) replaceData(list)
                        else addData(list)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) mCourseAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }

            })
        }
    }

    fun refresh() {
        eduMainViewModel.getEduMainCourseList(true)
    }

    fun initTitleBar() {
        var builder: AddressSelector.Builder

        binding.titleBar.setOnViewClick(object : onViewClick {
            override fun rightClick(view: View?) {
                //条件选择器
//                val pvOptions: OptionsPickerView<*> = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, option2, options3, v -> //返回的分别是三个级别的选中位置
//                    val tx: String = (options1Items.get(options1).getPickerViewText()
//                            + options2Items.get(options1).get(option2)
//                            + options3Items.get(options1).get(option2).get(options3).getPickerViewText())
//                    tvOptions.setText(tx)
//                }).build<Any>()
//                pvOptions.setPicker(options1Items, options2Items, options3Items)
//                pvOptions.show()

            }

            override fun leftClick(view: View?) {
                GlobalScope.coroutineContext.run {
                    context?.let {
                        builder = AddressSelectorKt.build(it)
                        builder.options2Item.observe(viewLifecycleOwner, Observer<String> {
                            binding.titleBar.setLeftText(it)
                        }
                        )
                        builder.showPickerView()
                    }
                }
            }

        })
    }

    private fun initRecycleView() {
        binding.doorToDoorRecycleView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        mCourseAdapter.run {
            setOnItemClickListener { _, _, position ->
//                val bundle = bundleOf(BrowserActivity.URL to mCourseAdapter.data[position].link)
//                NavHostFragment.findNavController(this@EduMainFragment).navigate(R.id.action_tab_to_browser, bundle)
            }
            onItemChildClickListener = this@EduMainFragment.onItemChildClickListener
//            if (headerLayoutCount > 0) removeAllHeaderView()
//            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, courseRecycleView)
        }
    }

    private fun initBanner() {

        banner.run {
//            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200))
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

//    private val list: MutableList<String> = ArrayList()

    fun initDoorToDoorSpinner() {
        //第一步：定义下拉列表内容
        val grade = resources.getStringArray(R.array.Grade)
        //第二步：为下拉列表定义一个适配器
        val mDoorToDoorSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, grade)
        //第三步：设置下拉列表下拉时的菜单样式
        mDoorToDoorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //第四步：将适配器添加到下拉列表上
        binding.doorToDoorSpinner.adapter = mDoorToDoorSpinnerAdapter
        //第五步：添加监听器，为下拉列表设置事件的响应
        binding.doorToDoorSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
//        binding.doorToDoorSpinner.onItemClickListener = (AdapterView.OnItemClickListener { parent, view, position, id -> TODO("Not yet implemented") })
        //将spinnertext添加到OnTouchListener对内容选项触屏事件处理
//        binding.doorToDoorSpinner.setOnTouchListener(object : OnTouchListener() {
//            fun onTouch(v: View, event: MotionEvent?): Boolean {
//                // TODO Auto-generated method stub
//                // 将mySpinner隐藏
//                v.visibility = View.INVISIBLE
//                Log.i("spinner", "Spinner Touch事件被触发!")
//                return false
//            }
//        })
        //焦点改变事件处理
        binding.doorToDoorSpinner.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                // TODO Auto-generated method stub
                v.visibility = View.VISIBLE
                Log.i("spinner", "Spinner FocusChange事件被触发！")
            }
        })
    }

    private fun loadMore() {
        eduMainViewModel.getEduMainCourseList(false)
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
//        when (view.id) {
//            R.id.articleStar -> {
//                if (isLogin) {
//                    mCourseAdapter.run {
//                        data[position].run {
//                            collect = !collect
//                            eduMainViewModel.collectArticle(id, collect)
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