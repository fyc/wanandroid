package luyao.wanandroid.ui.category

import android.net.Uri
import luyao.mvvm.core.util.Timer
import luyao.wanandroid.APP_START
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.ItemCategoryHomeItemBinding

class CateItemAdapter(layoutResId: Int = R.layout.item_category_home_item) : BaseBindAdapter<Data>(layoutResId, BR.menuAdapter) {

    override fun convert(helper: BindViewHolder, item: Data) {
        super.convert(helper, item)
        helper.setText(R.id.item_home_name, item.title)
        val uri = Uri.parse(item.imgURL)
        (helper.binding as ItemCategoryHomeItemBinding).itemAlbum.setImageURI(uri)

        Timer.stop(APP_START)
    }
}