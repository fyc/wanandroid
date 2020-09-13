package luyao.wanandroid.ui.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import luyao.wanandroid.R;

/**
 * Created by fangyc on 2018/7/19.
 */

public class EduTitleBar extends ConstraintLayout {
    private ConstraintLayout layout_left, layout_right;
    private TextView tv_left, tv_title, tv_title2, tv_right;
    private ImageView iv_left, iv_right;
    private onViewClick mClick;

    public EduTitleBar(Context context) {
        this(context, null);
    }

    public EduTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EduTitleBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.edu_title_bar, this);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EduBarView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.EduBarView_titleLeftTextColor:
                    tv_left.setTextColor(array.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.EduBarView_titleLeftDrawble:
                    iv_left.setImageResource(array.getResourceId(attr, 0));
                    break;
                case R.styleable.EduBarView_titleLeftTextSize:
//                    tv_left.setTextSize(array.getDimension(attr,12f));
                    tv_left.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(attr, 0));
                    break;
                case R.styleable.EduBarView_titleLeftText:
                    tv_left.setText(array.getString(attr));
                    break;

                case R.styleable.EduBarView_titleCenterTextColor:
                    tv_title.setTextColor(array.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.EduBarView_titleCenterTitle:
                    tv_title.setText(array.getString(attr));
                    break;
                case R.styleable.EduBarView_titleCenterTextSize:
                    tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(attr, 0));
                    break;

                case R.styleable.EduBarView_titleCenterText2Color:
                    tv_title2.setTextColor(array.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.EduBarView_titleCenterTitle2:
                    tv_title2.setText(array.getString(attr));
                    break;
                case R.styleable.EduBarView_titleCenterText2Size:
//                    tv_title2.setTextSize(array.getDimensionPixelSize(attr, 12));
//                    int size = array.getDimensionPixelSize(attr, 0);
                    tv_title2.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(attr, 0));
                    break;
                case R.styleable.EduBarView_titleCenterTitle2Visibility:
                    String visible = array.getString(attr);
                    setVisibility(tv_title2,visible);
                    break;
                case R.styleable.EduBarView_titleCenterTitle2DrawableLeft:
                    Drawable drawableLeft = null;
                    drawableLeft = array.getDrawable(attr);
                    /// 这一步必须要做,否则不会显示.
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    tv_title2.setCompoundDrawables(drawableLeft, null, null, null);
                    break;

                case R.styleable.EduBarView_titleRightDrawable:
                    iv_right.setImageResource(array.getResourceId(attr, 0));
                    break;
                case R.styleable.EduBarView_titleRightText:
                    tv_right.setText(array.getString(attr));
                    break;
                case R.styleable.EduBarView_titleRightTextColor:
                    tv_right.setTextColor(array.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.EduBarView_titleRightTextSize:
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(attr, 0));
                    break;

                case R.styleable.EduBarView_titleCenterLeftDrawbleVisibility:
                    String visible2 = array.getString(attr);
                    setVisibility(iv_left,visible2);
                    break;
                case R.styleable.EduBarView_titleCenterRightDrawableVisibility:
                    String visible3 = array.getString(attr);
                    setVisibility(iv_right,visible3);
                    break;
            }
        }
        array.recycle();
        layout_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClick != null)
                    mClick.leftClick(view);
            }
        });
        layout_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClick != null)
                    mClick.rightClick(view);
            }
        });
    }


    private void setVisibility(View view,String visible){
        visible = TextUtils.isEmpty(visible) ? "visible" : visible;
        switch (visible) {
            case "gone":
                view.setVisibility(View.GONE);
                break;
            case "visible":
                view.setVisibility(View.VISIBLE);
                break;
            case "invisible":
                view.setVisibility(View.INVISIBLE);
                break;
            default:
                view.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView() {
        tv_left = findViewById(R.id.tv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_title2 = findViewById(R.id.tv_title_2);
        tv_right = findViewById(R.id.tv_right);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        layout_left = findViewById(R.id.layout_left);
        layout_right = findViewById(R.id.layout_right);
    }

    public void setOnViewClick(onViewClick click) {
        this.mClick = click;
    }

    //设置标题
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    //设置左标题
    public void setLeftText(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_left.setText(title);
        }
    }

    //设置右标题
    public void setRightText(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_right.setText(title);
        }
    }

    //设置标题大小
    public void setTitleSize(int size) {
        if (tv_title != null) {
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    //设置左标题大小
    public void setLeftTextSize(int size) {
        if (tv_left != null) {
            tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    //设置右标题大小
    public void setRightTextSize(int size) {
        if (tv_right != null) {
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    //设置左图标
    public void setLeftDrawable(int res) {
        if (iv_left != null) {
            iv_left.setImageResource(res);
        }
    }

    //设置右图标
    public void setRightDrawable(int res) {
        if (iv_right != null) {
            iv_right.setImageResource(res);
        }
    }

    public interface onViewClick {
        void leftClick(View view);

        void rightClick(View view);
    }
}
