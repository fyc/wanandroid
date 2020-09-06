package luyao.wanandroid.model.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import luyao.mvvm.core.Result
import luyao.wanandroid.databean.EduResponse
import java.io.IOException

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class EduBaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> EduResponse<T>): EduResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(response: EduResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {
            if (response.result == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.message))
            } else {
                successBlock?.let { it() }
                Result.Success(response.data)
            }
        }
    }


}