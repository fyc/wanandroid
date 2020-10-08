package luyao.wanandroid.ui.registinfo

import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.Result
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.CoroutinesDispatcherProvider
import luyao.wanandroid.checkResult
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.repository.LoginRepository
import java.io.IOException

/**
 * Created by luyao
 * on 2019/4/2 16:36
 */
class RegistInfoViewModel(val repository: RegistInfoRepository, val provider: CoroutinesDispatcherProvider) : BaseViewModel() {

    val mobile = ObservableField<String>("")
    val verificationCode = ObservableField<String>("")

    val toast = MutableLiveData<String>()


    private fun isInputValid(mobile: String, verificationCode: String) = mobile.isNotBlank() && verificationCode.isNotBlank()

    fun login() {

        launchOnUI {
        }
    }

    fun verification(textView: TextView) {
        launchOnUI {

        }
    }

}