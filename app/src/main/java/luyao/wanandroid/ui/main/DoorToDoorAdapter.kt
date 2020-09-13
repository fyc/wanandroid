package luyao.wanandroid.ui.main

import luyao.mvvm.core.util.Timer
import luyao.wanandroid.APP_START
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.model.bean.Article

/**
 * Created by luyao
 * on 2018/3/14 15:52
 */
class DoorToDoorAdapter(layoutResId: Int = R.layout.item_subject_view) : BaseBindAdapter<Article>(layoutResId, BR.article) {

    override fun convert(helper: BindViewHolder, item: Article) {
        super.convert(helper, item)

        helper.setText(R.id.subjectName, if (item.author.isBlank()) "分享者: ${item.shareUser}" else item.author)
        Timer.stop(APP_START)
    }
}