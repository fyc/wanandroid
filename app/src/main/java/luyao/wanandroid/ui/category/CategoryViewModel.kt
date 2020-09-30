package luyao.wanandroid.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.App
import luyao.wanandroid.databean.EduResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class CategoryViewModel : BaseViewModel() {
    val menuDataList: MutableLiveData<List<CategoryLeftData>> = MutableLiveData()

    val cateDataList: MutableLiveData<List<CategoryData>> = MutableLiveData()

    var currentIndex = 0

    fun loadLeftData() {
        viewModelScope.launch(Dispatchers.Main) {

            val input = App.CONTEXT.assets.open("categoryLeft.json")//传入文件名称 读取assets文件
            val results = StringBuilder()
            val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
                lines.forEach { results.append(it) }
            }

            val type: Type = object : TypeToken<EduResponse<List<CategoryLeftData>>>() {}.type
            val result = Gson().fromJson<EduResponse<List<CategoryLeftData>>>(results.toString(), type).data

            menuDataList.value = result
        }

    }

    fun loadRightData(diff: Int) {
        if (currentIndex + diff >= 0 && currentIndex + diff < menuDataList.value?.size!!) {
            cateDataList.value = null
            currentIndex += diff
            val input = App.CONTEXT.assets.open("category$currentIndex.json")//传入文件名称 读取assets文件
            val results = StringBuilder()
            val inputString = BufferedReader(InputStreamReader(input)).useLines { lines ->
                lines.forEach { results.append(it) }
            }

            val type: Type = object : TypeToken<EduResponse<List<CategoryData>>>() {}.type
            val result = Gson().fromJson<EduResponse<List<CategoryData>>>(results.toString(), type).data
            cateDataList.value = result
        }
    }

}