package luyao.mvvm.core.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.ArrayList

abstract class BaseVMActivity : AppCompatActivity() {

    protected inline fun <reified T : ViewDataBinding> binding(
            @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@BaseVMActivity
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermission()
        startObserve()
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()

    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
    val mPermissionList = ArrayList<String>()
    val mRequestCode = 0x1//权限请求码
    fun initPermission() {
        mPermissionList.clear()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                            this@BaseVMActivity,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                mPermissionList.add(permission)
            }
        }
        if (!mPermissionList.isEmpty()) {
            // 后续操作...
            ActivityCompat.requestPermissions(this@BaseVMActivity, permissions, mRequestCode)

        } else {
            Toast.makeText(this@BaseVMActivity,"全部授予！",Toast.LENGTH_SHORT).show()
        }
    }


    //重写
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0x1 -> for (i in 0 until grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) Toast.makeText(this, "您有未授予的权限，可能影响使用", Toast.LENGTH_SHORT).show()
            }

        }// 授权结束后的后续操作
    }

}