package luyao.wanandroid.util

import android.os.Build
import android.text.Html
import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest

class EncodeUtil private constructor() {
    companion object {
        const val MD5_Key = "K12EduMD5Key001"
        /**
         * 转为16进制
         */
        fun toHex(byteArray: ByteArray): String {
            with(StringBuilder()) {
                //转为字符串
                byteArray.forEach {
                    //        println(it)
                    var value = it
                    var hex = value.toInt() and (0xFF)
                    val toHexString = Integer.toHexString(hex)
                    if (toHexString.length == 1) {
                        this.append("0" + toHexString)
                    } else {
                        this.append(toHexString)
                    }
                }
//                println(this.length)
                Log.d("aaaaaaaaaaa", this.toString().toUpperCase())
                return this.toString()
            }
        }

        fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            val digest = md.digest(input.toByteArray())
//            println("MD5加密前 ： " + digest.size)
            return toHex(digest)
        }

        /**
         * Return the urlencoded string.
         *
         * @param input       The input.
         * @param charsetName The name of charset.
         * @return the urlencoded string
         */

        @JvmOverloads
        fun encode(input: String?, charsetName: String? = "UTF-8"): String {
            return if (input == null || input.isEmpty()) {
                ""
            } else try {
                URLEncoder.encode(input, charsetName)
            } catch (e: UnsupportedEncodingException) {
                throw AssertionError(e)
            }
        }
        /**
         * Return the string of decode urlencoded string.
         *
         * @param input       The input.
         * @param charsetName The name of charset.
         * @return the string of decode urlencoded string
         */
        /**
         * Return the string of decode urlencoded string.
         *
         * @param input The input.
         * @return the string of decode urlencoded string
         */
        @JvmOverloads
        fun decode(input: String?, charsetName: String? = "UTF-8"): String {
            return if (input == null || input.length == 0) "" else try {
                val safeInput = input.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")
                        .replace("\\+".toRegex(), "%2B")
                URLDecoder.decode(safeInput, charsetName)
            } catch (e: UnsupportedEncodingException) {
                throw AssertionError(e)
            }
        }

        /**
         * Return Base64-encode bytes.
         *
         * @param input The input.
         * @return Base64-encode bytes
         */
        fun base64Encode(input: String): ByteArray {
            return base64Encode(input.toByteArray())
        }

        /**
         * Return Base64-encode bytes.
         *
         * @param input The input.
         * @return Base64-encode bytes
         */
        fun base64Encode(input: ByteArray?): ByteArray {
            return if (input == null || input.size == 0) ByteArray(0) else Base64.encode(
                    input,
                    Base64.NO_WRAP
            )
        }

        /**
         * Return Base64-encode string.
         *
         * @param input The input.
         * @return Base64-encode string
         */
        fun base64Encode2String(input: ByteArray?): String {
            return if (input == null || input.size == 0) "" else Base64.encodeToString(
                    input,
                    Base64.NO_WRAP
            )
        }

        fun base64Encode2String(input: String): String {
            if (input.isNullOrEmpty()) return ""
            return Base64.encodeToString(
                    input.toByteArray(),
                    Base64.NO_WRAP
            )
        }

        /**
         * Return the bytes of decode Base64-encode string.
         *
         * @param input The input.
         * @return the string of decode Base64-encode string
         */
        fun base64Decode(input: String?): ByteArray {
            return if (input == null || input.length == 0) ByteArray(0) else Base64.decode(
                    input,
                    Base64.NO_WRAP
            )
        }

        /**
         * Return the bytes of decode Base64-encode bytes.
         *
         * @param input The input.
         * @return the bytes of decode Base64-encode bytes
         */
        fun base64Decode(input: ByteArray?): ByteArray {
            return if (input == null || input.size == 0) ByteArray(0) else Base64.decode(
                    input,
                    Base64.NO_WRAP
            )
        }

        /**
         * Return html-encode string.
         *
         * @param input The input.
         * @return html-encode string
         */
        fun htmlEncode(input: CharSequence?): String {
            if (input == null || input.length == 0) return ""
            val sb = StringBuilder()
            var c: Char
            var i = 0
            val len = input.length
            while (i < len) {
                c = input[i]
                when (c) {
                    '<' -> sb.append("&lt;") //$NON-NLS-1$
                    '>' -> sb.append("&gt;") //$NON-NLS-1$
                    '&' -> sb.append("&amp;") //$NON-NLS-1$
                    '\'' -> sb.append("&#39;") //$NON-NLS-1$
                    '"' -> sb.append("&quot;") //$NON-NLS-1$
                    else -> sb.append(c)
                }
                i++
            }
            return sb.toString()
        }

        /**
         * Return the string of decode html-encode string.
         *
         * @param input The input.
         * @return the string of decode html-encode string
         */
        fun htmlDecode(input: String?): CharSequence {
            if (input == null || input.length == 0) return ""
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(input)
            }
        }

        /**
         * Return the binary encoded string padded with one space
         *
         * @param input The input.
         * @return binary string
         */
        fun binaryEncode(input: String): String {
            val stringBuilder = StringBuilder()
            for (i in input.toCharArray()) {
                stringBuilder.append(Integer.toBinaryString(i.toInt()))
                stringBuilder.append(' ')
            }
            return stringBuilder.toString()
        }

        /**
         * Return UTF-8 String from binary
         *
         * @param input binary string
         * @return UTF-8 String
         */
        fun binaryDecode(input: String): String {
            val splitted = input.split(" ").toTypedArray()
            val sb = StringBuilder()
            for (i in splitted) {
                sb.append(i.replace(" ", "").toInt(2).toChar())
            }
            return sb.toString()
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}