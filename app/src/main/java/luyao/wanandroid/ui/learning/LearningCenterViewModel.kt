package luyao.wanandroid.ui.learning

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
import luyao.wanandroid.ui.main.CourseData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class LearningCenterViewModel(private val repository: LearningCenterRepository) : BaseViewModel() {

    val auxiliaryDataList: MutableLiveData<List<AuxiliaryData>> = MutableLiveData()

    val mBanners: LiveData<List<LearningCenterBannerData>> = liveData {
        kotlin.runCatching {
            emit(getBanners())
        }
    }

    fun getBanners(): List<LearningCenterBannerData> {
        val input = App.CONTEXT.assets.open("banner.json")//传入文件名称 读取assets文件
        val results = StringBuilder()
        val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
            lines.forEach { results.append(it) }
        }

        val type: Type = object : TypeToken<EduResponse<List<LearningCenterBannerData>>>() {}.type
        return Gson().fromJson<EduResponse<List<LearningCenterBannerData>>>(results.toString(), type).data
    }

    fun getAuxiliaryData() {
        viewModelScope.launch(Dispatchers.Main) {

            val input = App.CONTEXT.assets.open("auxiliary.json")//传入文件名称 读取assets文件
            val results = StringBuilder()
            val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
                lines.forEach { results.append(it) }
            }

            val type: Type = object : TypeToken<EduResponse<List<AuxiliaryData>>>() {}.type
            val result = Gson().fromJson<EduResponse<List<AuxiliaryData>>>(results.toString(), type).data

            auxiliaryDataList.value = result
        }

    }


}