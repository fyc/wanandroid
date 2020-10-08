package luyao.wanandroid.ui.user


/**
 * Created by Lu
 * on 2018/4/5 08:02
 */

/*
 * {
    "data": {
        "admin": false,
        "chapterTops": [],
        "collectIds": [
            8259,
            8251,
            8072,
            8273,
            8160,
            8386,
            8695,
            9607,
            10022,
            9766,
            10825
        ],
        "email": "",
        "icon": "",
        "id": 22057,
        "nickname": "秉心说",
        "password": "",
        "publicName": "秉心说",
        "token": "",
        "type": 0,
        "username": "秉心说___"
    },
    "errorCode": 0,
    "errorMsg": ""
}
 */
data class UserData(
    val createDate: String,
    val gradeNum: Int,
    val id: String,
    val isNewRecord: Boolean,
    val latitude: Double,
    val longitude: Double,
    val mobile: String,
    val stage: Int
)