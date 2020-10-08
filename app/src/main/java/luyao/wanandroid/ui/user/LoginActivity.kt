package luyao.wanandroid.ui.user

import android.app.ProgressDialog
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import luyao.mvvm.core.base.BaseVMActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityLogin2Binding
import luyao.wanandroid.model.bean.Title
import luyao.wanandroid.ui.NavigationActivity
import luyao.wanandroid.ui.registinfo.RegistInfoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LoginActivity : BaseVMActivity() {

    private val mViewModel by viewModel<UserViewModel>()
    private val binding by binding<ActivityLogin2Binding>(R.layout.activity_login2)

    override fun initView() {
        binding.run {
            viewModel = mViewModel
            title = Title(R.string.login, R.drawable.arrow_back) { onBackPressed() }

            btnVerification.setOnClickListener {
                mViewModel.verification(it as TextView)
            }
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
        mViewModel.apply {

            uiState.observe(this@LoginActivity, Observer {
                if (it.isLoading) showProgressDialog()

                it.isSuccess?.let {
                    toast("登录成功")
                    dismissProgressDialog()
//                    NavigationActivity.start(this@LoginActivity)
                    RegistInfoActivity.start(this@LoginActivity)
                    finish()

                }

                it.isError?.let { err ->
                    dismissProgressDialog()
                    toast(err)
                }
            })
            toast.observe(this@LoginActivity, Observer {
                toast(it)
            })
        }
    }

//    private var progressDialog: ProgressDialog? = null
//    private fun showProgressDialog() {
//        if (progressDialog == null)
//            progressDialog = ProgressDialog(this)
//        progressDialog?.show()
//    }
//
//    private fun dismissProgressDialog() {
//        progressDialog?.dismiss()
//    }

}