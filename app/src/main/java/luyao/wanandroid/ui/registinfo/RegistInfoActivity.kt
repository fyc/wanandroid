package luyao.wanandroid.ui.registinfo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import luyao.mvvm.core.base.BaseVMActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityRegistInfoBinding
import luyao.wanandroid.model.bean.Title
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistInfoActivity : BaseVMActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent()
            //获取intent对象
            intent.setClass(context, RegistInfoActivity::class.java)
            context.startActivity(intent)
        }
    }


    private val mViewModel by viewModel<RegistInfoViewModel>()
    private val binding by binding<ActivityRegistInfoBinding>(R.layout.activity_regist_info)
    private val titles = arrayOf("我是老是", "我是学生")
    override fun initView() {
        binding.run {
            viewModel = mViewModel
            title = Title(R.string.login, R.drawable.arrow_back) { onBackPressed() }

            for (i in titles.indices) {
                tablayout.getTabAt(i)?.text = titles[i]
                var tabitem = tablayout.newTab()
                val view: View =
                        LayoutInflater.from(this@RegistInfoActivity).inflate(R.layout.regist_info_tab, null)
                var tv = view as TextView
                tv.text = titles[i]
                tabitem.customView = view
                tablayout.addTab(tabitem)
            }
            tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab) {
                }

                override fun onTabUnselected(p0: TabLayout.Tab) {
                    (p0.customView as TextView).setTextColor(Color.GRAY)
                }

                override fun onTabSelected(p0: TabLayout.Tab) {
                    (p0.customView as TextView).setTextColor(Color.BLACK)
                }
            })
        }

    }

    override fun initData() {
    }

    override fun startObserve() {
        mViewModel.apply {

            toast.observe(this@RegistInfoActivity, Observer {
                toast(it)
            })
        }
    }


}