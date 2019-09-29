package com.example.android_canteen;

import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.example.android_canteen.base.BaseConstant;
import com.example.android_canteen.newwork.FastJsonRequest;
import com.example.android_canteen.newwork.FastJsonRequestPost;
import com.example.android_canteen.newwork.GetData;
import com.example.android_canteen.newwork.OkHttpStack;
import com.example.android_canteen.observer.ObserverManager;
import com.example.android_canteen.requestbean.BaseBeanReq;
import com.example.android_canteen.utils.AuthcodeTwo;
import com.example.android_canteen.utils.CardUtils;
import com.example.android_canteen.utils.MyHashMap;
import com.example.android_canteen.utils.Object2Map;
import java.io.File;
import java.io.InputStream;

import android_api.SerialPort;
import okhttp3.OkHttpClient;



public class MyApp extends Application {

    static MyApp app;

    public RequestQueue queue;

    OkHttpClient mOkHttpClient = new OkHttpClient();

    private SerialPort mSerialPort;
    private SerialPort mSerialPort2;

    private InputStream mInputStream;
    private InputStream mInputStream2;

    private volatile ReadThread mReadThread;


    private final Handler cardHandler = new Handler();

    private boolean mFingerPrintConnected;
    private final Handler mFingerPrintHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        queue = Volley.newRequestQueue(this, new OkHttpStack());

//        CrashReport.initCrashReport(getApplicationContext(), "67ead733ff", false);


//        PreferencesUtil.initPrefs(this);
//        PreferencesUtil.putInt("TYPE_PREVIEW_ANGLE", 0);
//        GlobalSet.setLiveStatusValue(GlobalSet.LIVE_STATUS.values()[0]);

        Utils.init(this);

        //bugly初始化
        if (ScreenUtils.isTablet()) {
            /**刷卡器线程*/
//            readCard();


            /**指纹模块线程*/
        }


    }

    private void readCard() {
        //获取串口实例
        try {
            mSerialPort = new SerialPort(new File(SPUtils.getInstance().getString("serialPort", BaseConstant.SPORT)), BaseConstant.IBAUDRATE, 0);
            Log.e("初始化串口", "readCard: " + SPUtils.getInstance().getString("serialPort", BaseConstant.SPORT));
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读串口线程
     */
    private class ReadThread extends Thread {
        @Override
        public void run() {
            while (mInputStream != null && mReadThread == Thread.currentThread()) {
                byte[] buffer = new byte[64];
                try {
                    int size = mInputStream.read(buffer);
                    if (size > 0) {

                        String cardNo;

                        cardNo = CardUtils.getInstance().getCardNo(buffer, 0, size);

                        if (!TextUtils.isEmpty(cardNo))
                            cardHandler.post(new CardBroadcast(cardNo));
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private class CardBroadcast implements Runnable {

        private String cardNo;

        private CardBroadcast(String cardNo) {
            this.cardNo = cardNo;
        }

        @Override
        public void run() {
            ObserverManager.getInstance().notifyObserver(cardNo);
        }
    }
    public long time = System.currentTimeMillis() / 1000;
    public void DisPachTime() {
        this.time = System.currentTimeMillis() / 1000;

        Log.e("TAG", "DisPachTime: ");
    }

    public static MyApp getInstance() {
        return app;
    }

    public <T> void requestData(Object tag, BaseBeanReq<T> object, Response.Listener<T> listener,
                                Response.ErrorListener errorListener) {

        MyHashMap map = new MyHashMap();

        map.putAll(Object2Map.object2Map(object));

        String encryStr = AuthcodeTwo.authcodeEncode(map.toString(), GetData.URL_KEY);

        FastJsonRequest<T> request = new FastJsonRequest<>(GetData.requestUrl(object),
                object.myTypeReference(), listener, errorListener,
                encryStr);


        request.setShouldCache(true);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(tag);

        queue.add(request);

    }
    public <T> void request190Data(Object tag, BaseBeanReq<T> object, Response.Listener<T> listener,
                                   Response.ErrorListener errorListener) {

        MyHashMap map = new MyHashMap();

        map.putAll(Object2Map.object2Map(object));

        String encryStr = AuthcodeTwo.authcodeEncodeFace(map.toString(), GetData.URL_KEY);

        FastJsonRequest<T> request = new FastJsonRequest<>(GetData.request190(object),
                object.myTypeReference(), listener, errorListener,
                encryStr);

        request.setShouldCache(true);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(tag);

        queue.add(request);

    }
    public <T> void requestData2(Object tag, BaseBeanReq<T> object, Response.Listener<T> listener,
                                 Response.ErrorListener errorListener) {

        MyHashMap map = new MyHashMap();

        map.putAll(Object2Map.object2Map(object));

        String encryStr = AuthcodeTwo.authcodeEncode(map.toString(), GetData.URL_KEY);

        FastJsonRequest<T> request = new FastJsonRequest<>(GetData.requestUrl2(object),
                object.myTypeReference(), listener, errorListener,
                encryStr);

        request.setShouldCache(true);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(tag);

        queue.add(request);

    }

    public <T> void requestDataPost(Object tag, BaseBeanReq<T> object, Response.Listener<T> listener,
                                    Response.ErrorListener errorListener) {

        MyHashMap map = new MyHashMap();

        map.putAll(Object2Map.object2Map(object));


        String encryStr = AuthcodeTwo.authcodeEncode(map.toString(), GetData.URL_KEY);
        map.put("input", encryStr);
        FastJsonRequestPost<T> request = new FastJsonRequestPost<>(GetData.requestUrl3(object),
                object.myTypeReference(), listener, errorListener,
                encryStr, map);

        request.setShouldCache(true);
        Log.e("TAG", "requestData2: " + encryStr);
        Log.e("TAG", "map: " + JSON.toJSON(map));
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(tag);

        queue.add(request);

    }


    public <T> void requestFaceData(Object tag, BaseBeanReq<T> object, Response.Listener<T> listener,
                                    Response.ErrorListener errorListener) {

        MyHashMap map = new MyHashMap();

        map.putAll(Object2Map.object2Map(object));

        String encryStr = AuthcodeTwo.authcodeEncodeFace(map.toString(), GetData.URL_KEY);

        FastJsonRequest<T> request = new FastJsonRequest<>(GetData.requestFaceUrl(object),
                object.myTypeReference(), listener, errorListener,
                encryStr);

        request.setShouldCache(true);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(tag);

        queue.add(request);

    }
}
