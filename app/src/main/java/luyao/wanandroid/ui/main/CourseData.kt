package luyao.wanandroid.ui.main

data class CourseData(
    val courseTags: List<String>,
    val id: Int,
    val imagePath: String,
    val name: String,
    val order: Int,
    val price: Int,
    val teachersTags: List<String>,
    val teachingAge: String,
    val teachingQuantity: String,
    val title: String,
    val url: String
)