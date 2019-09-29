package com.example.android_canteen;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.idl.facesdk.FaceAuth;
import com.baidu.idl.facesdk.callback.AuthCallback;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.android_canteen.base.BaseConstant;
import com.example.android_canteen.requestbean.ValidSerialKeyReq;
import com.example.android_canteen.responbean.ValidSerialKeyRsp;
import com.example.android_canteen.utils.LoadingTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    SweetAlertDialog pd;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.logo)
    ImageView logo;

    private FaceAuth faceAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        pd = new LoadingTools().pd(this);
        post=findViewById(R.id.post);
        post.setOnClickListener(this);
    }



    void request() {
        pd.show();
        ValidSerialKeyReq req = new ValidSerialKeyReq();
        req.serialKey = et1.getText().toString();
        req.machineCode = DeviceUtils.getAndroidID();
        MyApp.getInstance().requestData2(this, req, new listener(), new error());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post:

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
//                if (TextUtils.isEmpty(et1.getText())) {
//                    ToastUtils.showShort("请输入注册码");
//                } else if (TextUtils.isEmpty(et2.getText())) {
//                    ToastUtils.showShort("请输入百度授权码");
//                } else {
//                    request();
//                }
                break;
        }
    }

    class listener implements Response.Listener<ValidSerialKeyRsp> {

        @Override
        public void onResponse(ValidSerialKeyRsp rsp) {
            pd.dismiss();
            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                RegisterActivity.this.finish();
//                BaiduCode(key);
                setAuth(et2.getText().toString(), rsp);
            } else
                ToastUtils.showShort(rsp.info);
        }
    }

    class error implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            pd.dismiss();
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("网络设置").setContentText("网络异常，请检查网络。").setCancelText("取消").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
                }
            }).setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                    sweetAlertDialog.cancel();
                }
            }).show();
        }
    }

    void
    setAuth(String key, ValidSerialKeyRsp rsp) {
        faceAuth = new FaceAuth();
        // 建议3288板子flagsThreads设置2,3399板子设置4
        faceAuth.setAnakinThreadsConfigure(2, 1);
        //Hao
        initLicenseOnLine(key, rsp);
    }

    // 在线鉴权
    private void initLicenseOnLine(final String key, final ValidSerialKeyRsp rsp) {
        if (TextUtils.isEmpty(key)) {
            pd.dismiss();
            ToastUtils.showShort("请输入百度授权码");
            return;
        }
        faceAuth.initLicenseOnLine(this, key, new AuthCallback() {
            @Override
            public void onResponse(final int code, final String response, String licenseKey) {
                pd.dismiss();
                if (code == 0) {
                    SPUtils.getInstance().put(BaseConstant.REGISTER, et1.getText().toString());
                    SPUtils.getInstance().put(BaseConstant.REGISTERDATA, JSON.toJSONString(rsp));
                    SPUtils.getInstance().put(BaseConstant.BAIDUKEY, key);
//                    GlobalSet.FACE_AUTH_STATUS = 0;
//                    // 初始化人脸
//                    FaceSDKManager.getInstance().initModel(RegisterActivity.this);
//                    // 初始化数据库
//                    DBManager.getInstance().init(getApplicationContext());
//                    // 加载feature 内存
//                    FaceSDKManager.getInstance().setFeature();
//                    GlobalSet.setLicenseOnLineKey(key);
//                    GlobalSet.setLicenseStatus(GlobalSet.LICENSE_ONLINE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            RegisterActivity.this.finish();
                        }
                    });
                } else {
                    ToastUtils.showShort("授权码无效");
                }
            }
        });
    }

//    void setAuth(String key) {
//        if (!isDestroyed())
//            pd.show();
//        faceAuth = new FaceAuth();
//        // 建议3288板子flagsThreads设置2,3399板子设置4
//        faceAuth.setAnakinThreadsConfigure(2, 1);
//        initLicenseOnLine(key);
//    }
//
//    // 在线鉴权
//    private void initLicenseOnLine(final String key) {
//        if (TextUtils.isEmpty(key)) {
//            if (!isDestroyed())
//                pd.dismiss();
//            ToastUtils.showShort("请输入百度授权码");
//            return;
//        }
//        faceAuth.initLicenseOnLine(this, key, new AuthCallback() {
//            @Override
//            public void onResponse(final int code, final String response, String licenseKey) {
//                if (!isDestroyed())
//                    pd.dismiss();
//                if (code == 0) {
//                    SPUtils.getInstance().put(BaseConstant.BAIDUKEY, key);
//                    GlobalSet.FACE_AUTH_STATUS = 0;
//                    // 初始化人脸
//                    FaceSDKManager.getInstance().initModel(RegisterActivity.this);
//                    // 初始化数据库
//                    DBManager.getInstance().init(getApplicationContext());
//                    // 加载feature 内存
//                    FaceSDKManager.getInstance().setFeature();
//                    GlobalSet.setLicenseOnLineKey(key);
//                    GlobalSet.setLicenseStatus(GlobalSet.LICENSE_ONLINE);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                            RegisterActivity.this.finish();
//                        }
//                    });
//                } else {
//                    ToastUtils.showShort("授权码无效");
//                }
//            }
//        });
//    }
//
//    void BaiduCode(String code) {
//        if (!isDestroyed())
//            pd.show();
//        ValidRegistrationCodeReq req = new ValidRegistrationCodeReq();
//        req.registrationCode = code;
//        req.useEquipment = DeviceUtils.getAndroidID();
//        MyApp.getInstance().requestData2(this, req, new Baidulistener(code), new error());
//    }
//
//    class Baidulistener implements Response.Listener<ValidRegistrationCodeRsp> {
//
//        String key;
//
//        Baidulistener(String key) {
//            this.key = key;
//        }
//
//        @Override
//        public void onResponse(ValidRegistrationCodeRsp rsp) {
//            if (!isDestroyed())
//                pd.dismiss();
//            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
//                setAuth(key);
//            } else ToastUtils.showShort(rsp.info);
//        }
//    }
}
