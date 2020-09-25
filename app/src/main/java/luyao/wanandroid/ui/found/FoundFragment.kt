package luyao.wanandroid.ui.found

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_edu_main.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.address.AddressSelector
import luyao.wanandroid.databinding.FragmentFoundBinding
import luyao.wanandroid.databinding.FragmentLearningCenterBinding
import luyao.wanandroid.databinding.FragmentMineBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import org.koin.androidx.viewmodel.ext.android.viewModel


class FoundFragment : BaseVMFragment<FragmentFoundBinding>(R.layout.fragment_found) {

    private val mViewModel by viewModel<FoundViewModel>()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val mMessageAdapter by lazy { MessageAdapter() }

    override fun initView() {
        binding.run {
            viewModel = mViewModel
            messageAdapter = mMessageAdapter
        }
        initTitleBar()
        initRecycleView()
    }

    override fun initData() {
        mViewModel.getMessageDataList(true)
    }

    override fun startObserve() {
        mViewModel.apply {
            uiState.observe(viewLifecycleOwner, Observer {

                it.showSuccess?.let { list ->
                    mMessageAdapter.run {
                        this.setEnableLoadMore(false)
                        if (it.isRefresh) replaceData(list)
                        else addData(list)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) mMessageAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }

            })
        }
    }


    fun initTitleBar() {
    }

    private fun initRecycleView() {
//        binding.messageRecycleView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mMessageAdapter.run {
            setOnItemClickListener { _, _, position ->
//                val bundle = bundleOf(BrowserActivity.URL to mCourseAdapter.data[position].link)
//                NavHostFragment.findNavController(this@EduMainFragment).navigate(R.id.action_tab_to_browser, bundle)
            }
            onItemChildClickListener = this@FoundFragment.onItemChildClickListener
//            if (headerLayoutCount > 0) removeAllHeaderView()
//            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, courseRecycleView)
        }
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

    private fun loadMore() {
//        eduMainViewModel.getEduMainCourseList(false)
    }

}