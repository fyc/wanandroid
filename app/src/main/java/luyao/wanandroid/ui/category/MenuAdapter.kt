package luyao.wanandroid.ui.category

import android.graphics.Color
import luyao.mvvm.core.util.Timer
import luyao.wanandroid.APP_START
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter

class MenuAdapter(layoutResId: Int = R.layout.item_category_menu) : BaseBindAdapter<CategoryLeftData>(layoutResId, BR.menuAdapter) {

    var categoryViewModel: CategoryViewModel? = null

    //    var selectItem = 0
    override fun convert(helper: BindViewHolder, item: CategoryLeftData) {
        super.convert(helper, item)
        categoryViewModel?.let {
            if (helper.adapterPosition == it.currentIndex) {
                helper.setBackgroundColor(R.id.item_name, Color.WHITE)
                helper.setTextColor(R.id.item_name, mContext.getResources().getColor(R.color.green))
                helper.adapterPosition
            } else {
                helper.setBackgroundColor(R.id.item_name, mContext.getResources().getColor(R.color.background))
                helper.setTextColor(R.id.item_name, mContext.getResources().getColor(R.color.black))
            }
            helper.setText(R.id.item_name, item.moduleTitle)
        }

        Timer.stop(APP_START)
    }
}