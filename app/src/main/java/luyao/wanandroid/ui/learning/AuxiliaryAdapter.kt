package luyao.wanandroid.ui.learning

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
class AuxiliaryAdapter(layoutResId: Int = R.layout.item_auxiliary_view) : BaseBindAdapter<AuxiliaryData>(layoutResId, BR.auxiliaryData) {

    override fun convert(helper: BindViewHolder, item: AuxiliaryData) {
        super.convert(helper, item)

        helper.setText(R.id.name, item.name)
        Timer.stop(APP_START)
    }
}