//package luyao.wanandroid.ui.user;
//
//import android.os.CountDownTimer;
//import android.text.TextUtils;
//import android.widget.TextView;
//
//import com.csair.mmpmobile.component.network.CookieUtils;
//import com.csair.mmpmobile.component.network.RequestUtils;
//import com.csair.mmpmobile.config.MMPUrl;
//import com.soybeany.bdlib.basic.network.interfaces.IRequestCallback;
//import com.soybeany.bdlib.util.async.interfaces.IProgressDialogHolder;
//import com.soybeany.bdlib.util.display.ToastUtils;
//import com.soybeany.bdlib.util.text.StdHintUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static android.view.View.VISIBLE;
//
//public class VerificationCodePresenterImp {
//    private final String LOGIN_FORMERROR_PHONE = "手机号/验证码长度格式错误";
//    private final String LENGTH_EMPTY_PHONE = "请检查手机号/验证码输入";
//    ReGetVerifyCodeButtonController reGetVerifyCodeButtonController;
//
//    public void startMobileVerify(String userNo, String mobile, String mobileVerifyToken, IRequestCallback callback) {
//        boolean phoneTag = mobile.length() == 11;
//        if ((TextUtils.isEmpty(mobile))) {
//            ToastUtils.show("手机号输入为空");
//            return;
//        } else {
//            if (phoneTag) {
//                startMobileVerifyMethod(userNo, mobile, mobileVerifyToken, callback);
//            } else {
//                ToastUtils.show("手机号长度格式错误");
//                return;
//            }
//        }
//    }
//
//
//    /**
//     * 获取手机验证码
//     */
//    private void startMobileVerifyMethod(String userNo, String mobile, String mobileVerifyToken, IRequestCallback callback) {
//
//        // 创建请求体
//        Map<String, String> params = new HashMap<>();
//        params.put("userNo", userNo);
//        params.put("mobile", mobile);
//        params.put("mobileVerifyToken", mobileVerifyToken);
//        // 开始请求
//        CookieUtils.COOKIE = null;
//        RequestUtils.post(MMPUrl.MOBILEVERIFY, params, callback).dialogHolder(dialogHolder).setupCookie()
//                .stdHint(StdHintUtils.STD_LOGIN_HINT).startRequest();
//    }
//
//    public static class ReGetVerifyCodeButtonController {
//        TextView textView;
//
//        public ReGetVerifyCodeButtonController(TextView textView) {
//            this.textView = textView;
//        }
//
//        public void prepare() {
//            textView.setEnabled(false);
//            textView.setBackgroundColor(android.graphics.Color.parseColor("#808B96"));//还是利用Color类；
//        }
//
//        private CountDownTimer timer;
//
//        public void startCountDown() {
//            if (timer != null) {
//                return;
//            }
//            timer = new CountDownTimer(60 * 1000l, 1000l) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    if (textView.getVisibility() == VISIBLE) {
//                        int sec = (int) (millisUntilFinished / 1000l);
//                        if (sec <= 0) {
//                            textView.setText("重新获取");
//                        } else {
//                            textView.setText("重新获取(" + sec + ")");
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onFinish() {
//                    timer = null;
//                    textView.setText("重新获取");
//                    textView.setEnabled(true);
//                    textView.setBackgroundColor(android.graphics.Color.parseColor("#F2741D"));//还是利用Color类；
//                }
//            };
//            timer.start();
//        }
//
//        public void cancelCountDown() {
//            if (timer != null) {
//                timer.cancel();
//                timer = null;
//                textView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("重新获取");
//                        textView.setEnabled(true);
//                        textView.setBackgroundColor(android.graphics.Color.parseColor("#F2741D"));//还是利用Color类；
//                    }
//                });
//            }
//        }
//
//    }
//}
