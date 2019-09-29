package com.example.android_canteen.base;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android_canteen.MyApp;

public class BaseFragment extends Fragment {

    protected AppCompatActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyApp.getInstance().queue.cancelAll(this);
    }
}
