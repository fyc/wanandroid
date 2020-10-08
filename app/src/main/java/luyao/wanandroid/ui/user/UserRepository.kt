package luyao.wanandroid.ui.user

import com.google.gson.Gson
import luyao.mvvm.core.Result
import luyao.wanandroid.App
import luyao.wanandroid.R
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanService
import luyao.wanandroid.model.bean.EduResponse
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.util.EncodeUtil
import luyao.wanandroid.util.PhoneUtil
import luyao.wanandroid.util.Preference

/**
 * Created by luyao
 * on 2019/4/10 9:42
 */
class UserRepository(val service: WanService) : BaseRepository() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")


    suspend fun login(userName: String, code: String): Result<UserData> {
        return safeApiCall(call = { requestLogin(userName, code) },
                errorMessage = App.CONTEXT.getString(R.string.about))
    }

    private suspend fun requestLogin(mobile: String, code: String): Result<UserData> {

        val map: HashMap<String, Any> = HashMap()
        map.put("mobile", mobile)
        map.put("channelId", "001")
        map.put("imei", PhoneUtil.getDeviceImei())
        val currentTimeMillis = System.currentTimeMillis()
        map.put("longTimeStr", currentTimeMillis)
        val checkCode = EncodeUtil.base64Encode2String(EncodeUtil.md5(PhoneUtil.getDeviceImei() + EncodeUtil.MD5_Key + currentTimeMillis).toUpperCase())
        map.put("checkCode", checkCode)
        map.put("verifyCode", code)

        val response = service.login2(map)

        return executeResponse(response, {
            val user = response.data
            isLogin = true
            userJson = Gson().toJson(user)
            App.CURRENT_USER = user
        })
    }

    suspend fun sendSmsCode(mobile: String): EduResponse<Nothing> {
        return safeApiCall2(call = { requestSendSmsCode(mobile) })
    }

    private suspend fun requestSendSmsCode(mobile: String): EduResponse<Nothing> {

        val map: HashMap<String, Any> = HashMap()
        map.put("mobile", mobile)
        map.put("imei", PhoneUtil.getDeviceImei())
        val currentTimeMillis = System.currentTimeMillis()
        map.put("longTimeStr", currentTimeMillis)
        val checkCode = EncodeUtil.base64Encode2String(EncodeUtil.md5(PhoneUtil.getDeviceImei() + EncodeUtil.MD5_Key + currentTimeMillis).toUpperCase())
        map.put("checkCode", checkCode)

        val response = service.sendSmsCode(map)

        return response

//        return executeResponse(response, {
////            val user = response.data
////            isLogin = true
////            userJson = Gson().toJson(user)
////            App.CURRENT_USER = user
//        })
    }

    suspend fun register(userName: String, passWord: String): Result<User> {
        return safeApiCall(call = { requestRegister(userName, passWord) }, errorMessage = "注册失败")
    }

    private suspend fun requestRegister(userName: String, passWord: String): Result<User> {
        val response = service.register(userName, passWord, passWord)
        return executeResponse(response, { requestLogin(userName, passWord) })
    }

}