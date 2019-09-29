package com.example.android_canteen.base;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.util.ToastUtils;
import com.example.android_canteen.MyApp;
import com.example.android_canteen.observer.ObserverListener;
import com.example.android_canteen.observer.ObserverManager;
import com.example.android_canteen.observer2.ObserverListener2;
import com.example.android_canteen.observer2.ObserverManager2;
import com.example.android_canteen.utils.ActivityContrl;
import com.example.android_canteen.utils.L;


/**
 * Created by Hao on 2017/10/18.
 * 继承BaseActivity的Activity，不要重复添加友盟统计
 */
public class BaseActivity2 extends AppCompatActivity implements ObserverListener2, ObserverListener {
    MediaPlayer mp;
//    SweetAlertDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityContrl.add(this);
//        powerManager = (PowerManager)this.getSystemService(this.POWER_SERVICE);
//        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
//        pd = new LoadingTools().pd(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        wakeLock.acquire();
        ObserverManager.getInstance().add(this);
        ObserverManager2.getInstance().add(this);
        Log.e("TAG", "onResume: " );
        if (mp != null) {
            mp.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        wakeLock.release();
        ObserverManager2.getInstance().remove(this);
        ObserverManager.getInstance().remove(this);
        Log.e("TAG", "onPause: " );
        if (mp != null) {
            mp.pause();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MyApp.getInstance().DisPachTime();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        MyApp.getInstance().queue.cancelAll(this);
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        if (!isDestroyed()){
//            if (dialog!=null){
//                dialog.dismiss();
//                dialog.cancel();
//            }
        }
        super.onDestroy();
    }

    @Override
    public void observerUpData2(String cardNo) {
//        Identity(cardNo);


    }

    @Override

//    void Identity(String cardNo) {
//
////                        pd.show();
//
//        SignAttendanceReq req = new SignAttendanceReq();
//        req.officeId = SpData.User().officeId;
////        req.classesId = SpData.User().classesId;
//        req.cardNo = cardNo;
//        MyApp.getInstance().requestData(this, req, new signListenr(), new error());
//    }

//    @Override
    public void observerUpData(String cardNo) {
//        if (PowerUtils.power(24)){
////            startActivity(new Intent(this, AttendanceActivity.class));
//        }else {
//            //如果没有摄像头权限，直接考勤
//            Identity(cardNo);
//        }

    }

//    class signListenr implements Response.Listener<SignAttendanceRsp> {
//
//        @Override
//        public void onResponse(SignAttendanceRsp rsp) {
//            L.d("===>"+ JSON.toJSONString(rsp));
////            if (!isDestroyed())
////                pd.dismiss();
//            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
//                voice(rsp.data.name + rsp.data.info);
//                showDialog(rsp.data);
//            } else {
//                ToastUtils.showShort(rsp.info);
//            }
//        }
//    }

    class error implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
//            if (!isDestroyed())
//                pd.dismiss();
            ToastUtils.showShort("请求失败，请重试");
        }
    }

    void voice(String str) {
//        VoiceSynthesisReq req = new VoiceSynthesisReq();
//        req.voiceContent = URLEncoder.encode(str);
//        MyApp.getInstance().requestData(this, req, new voiceListener(), new voiceError());
    }

//    class voiceListener implements Response.Listener<VoiceSynthesisRsp> {
//
//        @Override
//        public void onResponse(VoiceSynthesisRsp rsp) {
//            if (rsp.status == BaseConstant.REQUEST_SUCCES && !TextUtils.isEmpty(rsp.data)) {
//                if (mp != null) {
//                    mp.stop();
//                    mp.release();
//                    mp = null;
//                }
//                mp = MediaPlayer.create(BaseActivity2.this, Uri.parse(GlideUtil.DataUrl(rsp.data)));
//                if (mp!=null){
//                    mp.start();
//                }
//
//            }
//        }
//    }

    class voiceError implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
//    void showDialog(SignAttendanceRsp.DataBean bean) {
//        if (dialog == null) {
//            dialog = new AttendanceDialog(this, bean);
//            dialog.show();
//        } else dialog.changeData(bean);
//    }
}
