package luyao.wanandroid.ui.user

import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView

class VerificationCountDownTimer(var textView: TextView) {
    fun prepare() {
        textView.isEnabled = false
        textView.setBackgroundColor(Color.parseColor("#808B96")) //还是利用Color类；
    }

    private var timer: CountDownTimer? = null
    fun startCountDown() {
        if (timer != null) {
            return
        }
        timer = object : CountDownTimer(60 * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                if (textView.visibility == View.VISIBLE) {
                    val sec = (millisUntilFinished / 1000L).toInt()
                    if (sec <= 0) {
                        textView.text = "重新获取"
                    } else {
                        textView.text = "重新获取($sec)"
                    }
                }
            }

            override fun onFinish() {
                timer = null
                textView.text = "重新获取"
                textView.isEnabled = true
                textView.setBackgroundColor(Color.parseColor("#F2741D")) //还是利用Color类；
            }
        }
        (timer as CountDownTimer).start()
    }

    fun cancelCountDown() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
            textView.post {
                textView.text = "重新获取"
                textView.isEnabled = true
                textView.setBackgroundColor(Color.parseColor("#F2741D")) //还是利用Color类；
            }
        }
    }

}