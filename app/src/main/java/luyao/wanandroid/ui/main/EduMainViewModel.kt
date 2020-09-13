package luyao.wanandroid.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.Result
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.App
import luyao.wanandroid.databean.EduJson
import luyao.wanandroid.databean.EduResponse
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Banner
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.repository.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class EduMainViewModel(private val eduMainRepository: EduMainRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<CourseUiModel>()
    val uiState: LiveData<CourseUiModel>
        get() = _uiState

    private var currentPage = 1

    val mBanners: LiveData<List<BannerData>> = liveData {
        kotlin.runCatching {
//            val data = eduMainRepository.getBanners()
//            if (data is Result.Success) emit(data.data
            emit(getBanners())
        }
    }
//    val mmBanners = MutableLiveData<List<BannerData>>()


    fun getBanners(): List<BannerData> {
        val input = App.CONTEXT.assets.open("banner.json")//传入文件名称 读取assets文件
        val results = StringBuilder()
        val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
            lines.forEach { results.append(it) }
        }

        val type: Type = object : TypeToken<EduResponse<List<BannerData>>>() {}.type
        return Gson().fromJson<EduResponse<List<BannerData>>>(results.toString(), type).data
    }

    // xml界面中使用
    val refreshEduCourse: () -> Unit = { getEduMainCourseList(true) }


    fun getEduMainCourseList(isRefresh: Boolean = false) = getCourseList(isRefresh)


    private fun getCourseList(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            emitCourseUiState(true)
            if (isRefresh) currentPage = 1

            val result = eduMainRepository.getArticleList(currentPage)

            if (result is Result.Success) {
                val list = result.data
                if (list.offset >= list.total) {
                    emitCourseUiState(showLoading = false, showEnd = true)
                    return@launch
                }
                currentPage++
                emitCourseUiState(showLoading = false, showSuccess = list, isRefresh = isRefresh)

            } else if (result is Result.Error) {
                emitCourseUiState(showLoading = false, showError = result.exception.message)
            }
        }
    }

    private fun emitCourseUiState(
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: ArticleList? = null,
            showEnd: Boolean = false,
            isRefresh: Boolean = false,
            needLogin: Boolean? = null
    ) {
        val uiModel = CourseUiModel(showLoading, showError, showSuccess, showEnd, isRefresh, needLogin)
        _uiState.value = uiModel
    }

    data class CourseUiModel(
            val showLoading: Boolean, // 展示loading
            val showError: String?, // 错误信息
            val showSuccess: ArticleList?, // 正确的结果
            val showEnd: Boolean, // 加载更多
            val isRefresh: Boolean, // 刷新
            val needLogin: Boolean? = null
    )

}