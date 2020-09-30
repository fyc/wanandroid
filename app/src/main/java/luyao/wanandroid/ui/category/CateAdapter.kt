package luyao.wanandroid.ui.category

import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import luyao.mvvm.core.util.Timer
import luyao.wanandroid.APP_START
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.ItemCategoryHomeBinding

class CateAdapter(layoutResId: Int = R.layout.item_category_home) : BaseBindAdapter<CategoryData>(layoutResId, BR.cateAdapter) {

    override fun convert(helper: BindViewHolder, item: CategoryData) {
        super.convert(helper, item)

        val adapter by lazy { CateItemAdapter() }
        adapter.setNewData(item.dataList)
        helper.setText(R.id.blank, item.moduleTitle)
        val gridLayoutManager = GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (helper.binding as ItemCategoryHomeBinding).gridView.layoutManager = gridLayoutManager
        (helper.binding as ItemCategoryHomeBinding).gridView.adapter = adapter

        Timer.stop(APP_START)
    }
}