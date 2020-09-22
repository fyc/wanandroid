package luyao.wanandroid.ui.mine;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textview.MaterialTextView;

import luyao.wanandroid.R;

/**
 * Created by fangyc on 2018/7/19.
 */

public class ItemMineFunView extends ConstraintLayout {


    //    private LearningCenterCardViewBinding binding;
    ConstraintLayout constraintLayout;
    MaterialTextView tv_name, tv_content, img_more;

    public ItemMineFunView(Context context) {
        this(context, null);
    }

    public ItemMineFunView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemMineFunView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_mine_functiom, this);
//        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
//                R.layout.learning_center_card_view, null, false);
        initView();
    }


    private void initView() {
        constraintLayout = findViewById(R.id.constraintLayout);
        tv_name = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_content = findViewById(R.id.tv_button);
    }
}
