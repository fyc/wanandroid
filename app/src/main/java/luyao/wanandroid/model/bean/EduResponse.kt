package luyao.wanandroid.model.bean

/**
 * Created by luyao
 * on 2018/3/13 14:38
 */
data class EduResponse<out T>(val relust: Int, val message: String, val data: T)