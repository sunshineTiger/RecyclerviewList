package com.zhanghong.recyclerviewlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHong on 2017/3/22.
 */

public class LetterView extends LinearLayout {
    private Context mContext;
    private CharacterClickListener mListener;
    private String tag = LetterView.class.getSimpleName();
    private List<String> num;

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(VERTICAL);
    }


    public void upDataLetter(List<String> num) {
        this.num = num;
        for (String letter : num) {
            final String character = letter;
            TextView textView = (TextView) buildTextLayout(character);
            addView(textView);
        }
        invalidate();
    }

    private View buildTextLayout(final String character) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);
        tv.setText(character);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.clickCharacter(character);
                }
            }
        });
        return tv;
    }

    public void setCharaterListener(CharacterClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(tag, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(tag, "ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() >= 0 && event.getY() <= getHeight()) {
                    Log.e(tag, "getY():" + getY());
                    int itemHeight = getHeight() / num.size();
                    int index = (int) event.getY() / itemHeight;
                    if (null != mListener) {

                        mListener.clickCharacter(num.get(index));
                    }
                }
                break;
        }
        return true;
    }

    public void removeStart(List<String> num) {
//        this.num.clear();
        this.removeAllViews();
        this.num = num;
        for (String letter : num) {
            final String character = letter;
            TextView textView = (TextView) buildTextLayout(character);
            addView(textView);
        }
        invalidate();
    }

    public void addStart(List<String> num) {
//        this.num.clear();
        this.removeAllViews();
        this.num = num;
        for (String letter : num) {
            final String character = letter;
            TextView textView = (TextView) buildTextLayout(character);
            addView(textView);
        }
        invalidate();
    }

}
