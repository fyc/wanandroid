package luyao.wanandroid.ui.mine

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
import luyao.wanandroid.ui.main.EduMainRepository
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class MineViewModel(private val eduMainRepository: EduMainRepository) : BaseViewModel() {

    val commonFunDataList: MutableLiveData<List<CommonFunData>> = MutableLiveData()


    val mBanners: LiveData<List<BannerData>> = liveData {
        kotlin.runCatching {
            emit(getBanners())
        }
    }

    fun getBanners(): List<BannerData> {
        val input = App.CONTEXT.assets.open("banner.json")//传入文件名称 读取assets文件
        val results = StringBuilder()
        val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
            lines.forEach { results.append(it) }
        }

        val type: Type = object : TypeToken<EduResponse<List<BannerData>>>() {}.type
        return Gson().fromJson<EduResponse<List<BannerData>>>(results.toString(), type).data
    }

    fun getCommonFunData() {
        viewModelScope.launch(Dispatchers.Main) {

            val input = App.CONTEXT.assets.open("commonFun.json")//传入文件名称 读取assets文件
            val results = StringBuilder()
            val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
                lines.forEach { results.append(it) }
            }

            val type: Type = object : TypeToken<EduResponse<List<CommonFunData>>>() {}.type
            val result = Gson().fromJson<EduResponse<List<CommonFunData>>>(results.toString(), type).data

            commonFunDataList.value = result
        }

    }

}