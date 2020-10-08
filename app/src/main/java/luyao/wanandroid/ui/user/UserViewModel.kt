package luyao.wanandroid.ui.user

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
class UserViewModel(val repository: UserRepository, val provider: CoroutinesDispatcherProvider) : BaseViewModel() {

    val mobile = ObservableField<String>("")
    val verificationCode = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginUiState<UserData>>()
    val uiState: LiveData<LoginUiState<UserData>>
        get() = _uiState

    val toast = MutableLiveData<String>()


    private fun isInputValid(mobile: String, verificationCode: String) = mobile.isNotBlank() && verificationCode.isNotBlank()

    fun loginDataChanged() {
        _uiState.value = LoginUiState(enableLoginButton = isInputValid(mobile.get()
                ?: "", verificationCode.get() ?: ""))
    }

    fun mobileDataChanged() {
        _uiState.value = LoginUiState(enableVerification = !mobile.get().isNullOrBlank())
    }

    fun login() {

        launchOnUI {
            if (mobile.get().isNullOrBlank() || verificationCode.get().isNullOrBlank()) {
                _uiState.value = LoginUiState(enableLoginButton = false)
                return@launchOnUI
            }

            _uiState.value = LoginUiState(isLoading = true)

            val result = repository.login(mobile.get() ?: "", verificationCode.get() ?: "")

            result.checkResult(
                    onSuccess = {
                        _uiState.value = LoginUiState(isSuccess = it, enableLoginButton = true)
                    },
                    onError = {
                        _uiState.value = LoginUiState(isError = it)
                    })
        }
    }

    fun verification(textView: TextView) {
        launchOnUI {

            if (mobile.get().isNullOrBlank()) {
                _uiState.value = LoginUiState(enableVerification = false)
                return@launchOnUI
            }

            val verification = VerificationCountDownTimer(textView)
            verification.prepare()
            verification.startCountDown()

//            _uiState.value = LoginUiState(isLoading = true)

            val result = repository.sendSmsCode(mobile.get() ?: "")

            result.relust
            if (result.relust == 1) {
                toast.value = "获取验证码成功"
            } else {
                toast.value = "获取验证码失败"
            }
        }
    }


    val verifyInput: (String) -> Unit = { loginDataChanged() }
    val mobileInput: (String) -> Unit = { mobileDataChanged() }
}