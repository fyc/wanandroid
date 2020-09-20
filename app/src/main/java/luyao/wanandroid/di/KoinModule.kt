package luyao.wanandroid.di

import luyao.wanandroid.CoroutinesDispatcherProvider
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.api.WanService
import luyao.wanandroid.model.repository.*
import luyao.wanandroid.ui.learning.LearningCenterRepository
import luyao.wanandroid.ui.learning.LearningCenterViewModel
import luyao.wanandroid.ui.login.LoginViewModel
import luyao.wanandroid.ui.main.EduMainRepository
import luyao.wanandroid.ui.main.EduMainViewModel
import luyao.wanandroid.ui.navigation.NavigationViewModel
import luyao.wanandroid.ui.project.ProjectViewModel
import luyao.wanandroid.ui.search.SearchViewModel
import luyao.wanandroid.ui.share.ShareViewModel
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.ui.system.SystemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by luyao
 * on 2019/11/15 15:44
 */

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ArticleViewModel(get(), get(), get(), get(), get()) }
    viewModel { SystemViewModel(get(), get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { ProjectViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { ShareViewModel(get()) }
    viewModel { EduMainViewModel(get()) }
    viewModel { LearningCenterViewModel(get()) }
}

val repositoryModule = module {
    single { WanRetrofitClient.getService(WanService::class.java, WanService.BASE_URL) }
    single { CoroutinesDispatcherProvider() }
    single { LoginRepository(get()) }
    single { SquareRepository() }
    single { HomeRepository() }
    single { ProjectRepository() }
    single { CollectRepository() }
    single { SystemRepository() }
    single { NavigationRepository() }
    single { SearchRepository() }
    single { ShareRepository() }
    single { EduMainRepository() }
    single { LearningCenterRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)