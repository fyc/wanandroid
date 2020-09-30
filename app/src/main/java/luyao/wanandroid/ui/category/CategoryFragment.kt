package luyao.wanandroid.ui.category

import androidx.lifecycle.Observer
import com.facebook.drawee.backends.pipeline.Fresco
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import luyao.mvvm.core.base.BaseVMFragment
import luyao.wanandroid.R
import luyao.wanandroid.databinding.FragmentCategoryBinding
import luyao.wanandroid.util.Preference
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment : BaseVMFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val mViewModel by viewModel<CategoryViewModel>()
    private val mMenuAdapter by lazy { MenuAdapter() }
    private val mCateAdapter by lazy { CateAdapter() }
    private val isLogin by Preference(Preference.IS_LOGIN, false)

    override fun initView() {
        binding.run {
            viewModel = mViewModel
            menuAdapter = mMenuAdapter
            cateAdapter = mCateAdapter
        }
        Fresco.initialize(activity)

        mMenuAdapter.categoryViewModel = mViewModel
        mMenuAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.currentIndex = position
                notifyDataSetChanged()
                mViewModel.loadRightData(0)
                binding.lvHome.smoothScrollToPosition(0)
            }
        }

        binding.refHome.setOnRefreshListener(OnRefreshListener { refreshlayout ->
            mViewModel.loadRightData(-1)
            refreshlayout.finishRefresh(200 /*,false*/) //传入false表示刷新失败
            mMenuAdapter.notifyDataSetChanged()
            binding.lvHome.smoothScrollToPosition(0)
        })
        binding.refHome.setOnLoadMoreListener(OnLoadMoreListener { refreshlayout ->
            mViewModel.loadRightData(1)
            refreshlayout.finishLoadMore(200 /*,false*/) //传入false表示加载失败
            mMenuAdapter.notifyDataSetChanged()
            binding.lvHome.smoothScrollToPosition(0)
        })
    }

    override fun initData() {
        mViewModel.loadLeftData()
    }

    private fun initAdapter() {

    }

    override fun startObserve() {
        mViewModel.apply {
            menuDataList.observe(viewLifecycleOwner, Observer {
                mMenuAdapter.run {
                    this.setEnableLoadMore(false)
                    setNewData(it)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                    mViewModel.loadRightData(0)
                }
            })

            cateDataList.observe(viewLifecycleOwner, Observer {
                mCateAdapter.run {
                    this.setEnableLoadMore(false)
                    setNewData(it)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            })
        }
    }

}