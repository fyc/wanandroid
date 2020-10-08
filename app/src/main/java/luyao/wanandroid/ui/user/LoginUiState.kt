package luyao.wanandroid.ui.user

import luyao.mvvm.core.base.BaseViewModel

/**
 * Created by luyao
 * on 2020/3/30 15:34
 */
class LoginUiState<T>(
        isLoading: Boolean = false,
        isSuccess: T? = null,
        isError: String? = null,
        val enableLoginButton: Boolean = false,
        val enableVerification: Boolean = true,
        val needLogin: Boolean = false
) : BaseViewModel.UiState<T>(isLoading, false, isSuccess, isError)
