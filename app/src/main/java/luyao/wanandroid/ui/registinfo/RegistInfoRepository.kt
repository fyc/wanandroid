package luyao.wanandroid.ui.registinfo

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
class RegistInfoRepository(val service: WanService) : BaseRepository() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

}