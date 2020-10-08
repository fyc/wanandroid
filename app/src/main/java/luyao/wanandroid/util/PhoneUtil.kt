package luyao.wanandroid.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import luyao.wanandroid.App


class PhoneUtil {
    companion object {
        /**
         * Desc: 获取手机相关信息
         */

        /**
         * 获取手机厂商
         */
        fun getManufacturer(): String = Build.MANUFACTURER

        /**
         * 获取产品名
         */
        fun getDeviceProduct(): String = Build.PRODUCT

        /**
         * 获取手机品牌
         */
        fun getDeviceBrand(): String = Build.BRAND

        /**
         * 获取手机型号
         */
        fun getDeviceModel(): String = Build.MODEL

        /**
         * 获取手机宽度(px)
         */
        fun getDeviceWidth(): Int = App.CONTEXT.resources.displayMetrics.widthPixels

        /**
         * 获取手机高度(px)
         */
        fun getDeviceHeight(): Int = App.CONTEXT.resources.displayMetrics.heightPixels

        /**
         * 获取手机imei
         * Android 6.0以上需要[Manifest.permission.READ_PHONE_STATE]权限，否则无法获得
         */
        fun getDeviceImei(): String {
            val manager: TelephonyManager = App.CONTEXT.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var imei = ""
            if (ContextCompat.checkSelfPermission(
                            App.CONTEXT,
                            Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    manager.imei
                } else ""
            }
            return imei
        }

        /**
         * 获取手机meid
         * Android 6.0以上需要[Manifest.permission.READ_PHONE_STATE]权限，否则无法获得
         */
        fun getDeviceMeid(context: Context): String {
            val manager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var meid = ""
            if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                meid = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    manager.meid
                } else ""
            }
            return meid
        }
    }

}