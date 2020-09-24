package luyao.wanandroid.ui.found

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_edu_main.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.wanandroid.R
import luyao.wanandroid.address.AddressSelector
import luyao.wanandroid.databinding.FragmentFoundBinding
import luyao.wanandroid.databinding.FragmentLearningCenterBinding
import luyao.wanandroid.databinding.FragmentMineBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
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
            messageDataList.observe(viewLifecycleOwner, Observer {
                mMessageAdapter.run {
                    this.setEnableLoadMore(false)
                    setNewData(it)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            })
        }
    }


    fun initTitleBar() {
    }

    private fun initRecycleView() {
        binding.messageRecycleView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }


    private fun loadMore() {
//        eduMainViewModel.getEduMainCourseList(false)
    }

}