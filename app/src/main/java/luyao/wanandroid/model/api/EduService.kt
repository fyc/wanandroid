package luyao.wanandroid.model.api

import luyao.wanandroid.databean.EduResponse
import luyao.wanandroid.model.bean.*
import luyao.wanandroid.ui.main.BannerData
import retrofit2.http.*


/**
 * Created by luyao
 * on 2018/3/13 14:33
 */
interface EduService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): EduResponse<ArticleList>

    @GET("/banner/json")
    suspend fun getBanner(): EduResponse<List<BannerData>>

    @GET("/tree/json")
    suspend fun getSystemType(): EduResponse<List<SystemParent>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): EduResponse<ArticleList>

    @GET("/navi/json")
    suspend fun getNavigation(): EduResponse<List<Navigation>>

    @GET("/project/tree/json")
    suspend fun getProjectType(): EduResponse<List<SystemParent>>

    @GET("/wxarticle/chapters/json")
    suspend fun getBlogType(): EduResponse<List<SystemParent>>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getBlogArticle(@Path("id") id: Int, @Path("page") page: Int): EduResponse<ArticleList>

    @GET("/project/list/{page}/json")
    suspend fun getProjectTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): EduResponse<ArticleList>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(@Path("page") page: Int): EduResponse<ArticleList>

    @GET("/friend/json")
    suspend fun getWebsites(): EduResponse<List<Hot>>

    @GET("/hotkey/json")
    suspend fun getHot(): EduResponse<List<Hot>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun searchHot(@Path("page") page: Int, @Field("k") key: String): EduResponse<ArticleList>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): EduResponse<User>

    @GET("/user/logout/json")
    suspend fun logOut(): EduResponse<Any>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): EduResponse<User>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticles(@Path("page") page: Int): EduResponse<ArticleList>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): EduResponse<ArticleList>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): EduResponse<ArticleList>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): EduResponse<ArticleList>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun shareArticle(@Field("title") title: String, @Field("link") url: String): EduResponse<String>

}