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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class FoundViewModel : BaseViewModel() {

    private var currentPage = 1
    var showLoading: Boolean = false // 展示loading
    val messageDataList: MutableLiveData<List<MessageData>> = MutableLiveData()

    // xml界面中使用
    val refreshMessage: () -> Unit = { getMessageDataList(true) }
    fun getMessageDataList(isRefresh: Boolean = false) = getMessageData(isRefresh)
    private fun getMessageData(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Main) {
            showLoading = true
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
            showLoading = false
            messageDataList.value = list
        }

    }

}