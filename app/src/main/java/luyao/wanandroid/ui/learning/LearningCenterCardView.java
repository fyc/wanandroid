package luyao.wanandroid.ui.learning;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textview.MaterialTextView;

import luyao.wanandroid.R;
import luyao.wanandroid.databinding.LearningCenterCardViewBinding;

/**
 * Created by fangyc on 2018/7/19.
 */

public class LearningCenterCardView extends ConstraintLayout {


    //    private LearningCenterCardViewBinding binding;
    ConstraintLayout constraintLayout;
    MaterialTextView tv_title, tv_content, tv_button;

    public LearningCenterCardView(Context context) {
        this(context, null);
    }

    public LearningCenterCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LearningCenterCardView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.learning_center_card_view, this);
//        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
//                R.layout.learning_center_card_view, null, false);
        initView();
    }


    private void initView() {
        constraintLayout = findViewById(R.id.constraintLayout);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_button = findViewById(R.id.tv_button);
    }

    public void setNoticeStatu() {
        constraintLayout.setBackground(getResources().getDrawable(R.drawable.bg_course_notice_card));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        tv_content.setTextColor(getResources().getColor(R.color.white));
        tv_button.setTextColor(Color.parseColor("#02A7F0"));
        tv_button.setBackground(getResources().getDrawable(R.drawable.while_corner_bg));
    }

    public void setFindStatu() {
        constraintLayout.setBackground(getResources().getDrawable(R.drawable.transparent_black_stroke));
        tv_title.setTextColor(Color.parseColor("#5E5E5E"));
        tv_content.setTextColor(Color.parseColor("#C7C7C7"));
        tv_button.setTextColor(getResources().getColor(R.color.white));
        tv_button.setBackground(getResources().getDrawable(R.drawable.blue_corner_bg));
    }
}
