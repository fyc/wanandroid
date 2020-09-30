package luyao.wanandroid.ui.category

data class CategoryData(
    val dataList: List<Data>,
    val interfaceLink: String,
    val moduleStyle: String,
    val moduleTitle: String,
    val moreIcon: String,
    val moreLinkParam: String,
    val moreLinkType: String,
    val moreText: String,
    val moreTextDisplay: String,
    val type: String
)

data class Data(
    val desc: String,
    val id: String,
    val imgURL: String,
    val linkParam: String,
    val linkType: String,
    val offline_time: String,
    val online_time: String,
    val title: String,
    val type: String
)