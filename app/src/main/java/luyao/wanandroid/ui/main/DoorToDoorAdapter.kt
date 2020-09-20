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
class DoorToDoorAdapter(layoutResId: Int = R.layout.item_subject_view) : BaseBindAdapter<CourseData>(layoutResId, BR.courseData) {

    override fun convert(helper: BindViewHolder, item: CourseData) {
        super.convert(helper, item)

        helper.setText(R.id.subjectName, item.name)
        Timer.stop(APP_START)
    }
}