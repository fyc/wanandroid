package luyao.wanandroid.ui.found

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.App
import luyao.wanandroid.databean.EduResponse
import luyao.wanandroid.timetable.Course
import luyao.wanandroid.ui.main.CourseData
import luyao.wanandroid.ui.main.EduMainRepository
import luyao.wanandroid.ui.main.EduMainViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class FoundViewModel(private val eduMainRepository: EduMainRepository) : BaseViewModel() {

    private var currentPage = 1
    private val _uiState = MutableLiveData<UiModel>()
    val uiState: LiveData<UiModel>
        get() = _uiState

    // xml界面中使用
    val refreshMessage: () -> Unit = { getMessageDataList(true) }

    fun getMessageDataList(isRefresh: Boolean = false) = getMessageData(isRefresh)

    private fun getMessageData(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            emitUiState(true)
            if (isRefresh) currentPage = 1
            val input = App.CONTEXT.assets.open("foundMessage.json")//传入文件名称 读取assets文件
            val results = StringBuilder()
            val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
                lines.forEach { results.append(it) }
            }

            val type: Type = object : TypeToken<EduResponse<List<MessageData>>>() {}.type
            val result = Gson().fromJson<EduResponse<List<MessageData>>>(results.toString(), type).data

            val list = result
            currentPage++
            emitUiState(showLoading = false, showSuccess = list, isRefresh = isRefresh)
        }

    }

    private fun emitUiState(
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: List<MessageData>? = null,
            showEnd: Boolean = false,
            isRefresh: Boolean = false,
            needLogin: Boolean? = null
    ) {
        val uiModel = UiModel(showLoading, showError, showSuccess, showEnd, isRefresh, needLogin)
        _uiState.value = uiModel
    }

    data class UiModel(
            val showLoading: Boolean, // 展示loading
            val showError: String?, // 错误信息
            val showSuccess: List<MessageData>?, // 正确的结果
            val showEnd: Boolean, // 加载更多
            val isRefresh: Boolean, // 刷新
            val needLogin: Boolean? = null
    )

}