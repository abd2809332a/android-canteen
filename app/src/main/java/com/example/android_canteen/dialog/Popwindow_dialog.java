package com.example.android_canteen.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.android_canteen.R;


/**
 * Created by Administrator on 2019/5/15.
 */

public class Popwindow_dialog extends PopupWindow {
    Context context;

    int first=-1;
    TextView textView;


    public Popwindow_dialog(Context context) {
        this.context = context;

        init();
    }

    private void init() {


        final View mView = LayoutInflater.from(context).inflate(R.layout.layout_pop_dialog, null);

        final PopupWindow popupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);


        popupWindow.setFocusable(false);//获取焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            //检测popuwindwo消失
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });



    }



}
