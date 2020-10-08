package luyao.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import luyao.mvvm.core.base.BaseActivity
import luyao.wanandroid.R

/**
 * Created by luyao
 * on 2019/12/26 15:24
 */
class NavigationActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent()
            //获取intent对象
            intent.setClass(context, NavigationActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutResId() = R.layout.activity_navigation


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
    }

    override fun initData() {
    }
}