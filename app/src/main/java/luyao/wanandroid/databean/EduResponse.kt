package luyao.wanandroid.databean

/**
 * Created by luyao
 * on 2018/3/13 14:38
 */
data class EduResponse<out T>(val result: Int, val message: String, val data: T)

