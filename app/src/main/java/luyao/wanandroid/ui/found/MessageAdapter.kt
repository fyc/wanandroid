package luyao.wanandroid.ui.found

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
class MessageAdapter(layoutResId: Int = R.layout.item_found_message_constraint) : BaseBindAdapter<MessageData>(layoutResId, BR.messageAdapter) {

    override fun convert(helper: BindViewHolder, item: MessageData) {
        super.convert(helper, item)

        helper.setText(R.id.name, item.name)
        Timer.stop(APP_START)
    }
}