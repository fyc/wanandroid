package luyao.wanandroid.ui.learning

import luyao.mvvm.core.Result
import luyao.wanandroid.model.api.EduBaseRepository
import luyao.wanandroid.model.api.EduRetrofitClient
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.util.executeResponse

/**
 * Created by luyao
 * on 2019/4/10 14:09
 */
class LearningCenterRepository : EduBaseRepository() {

    suspend fun getBanners(): Result<List<LearningCenterBannerData>> {
        return safeApiCall(call = {requestBanners()},errorMessage = "")
    }

    private suspend fun requestBanners(): Result<List<LearningCenterBannerData>> =
        executeResponse(EduRetrofitClient.service.getLearningCenterBanner())


    suspend fun getArticleList(page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestArticleList(page) }, errorMessage = "")
    }

    private suspend fun requestArticleList(page: Int): Result<ArticleList> =
            executeResponse(EduRetrofitClient.service.getHomeArticles(page))
}