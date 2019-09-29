package com.example.android_canteen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
import com.example.android_canteen.common.GlobalSet;
import com.example.android_canteen.newwork.GetData;
import com.example.android_canteen.requestbean.GetAddressBySerialNumberReq;
import com.example.android_canteen.requestbean.LoginReq;
import com.example.android_canteen.requestbean.ValidSerialKeyReq;
import com.example.android_canteen.responbean.GetAddressBySerialNumberRsp;
import com.example.android_canteen.responbean.LoginRsp;
import com.example.android_canteen.responbean.ValidSerialKeyRsp;
import com.example.android_canteen.utils.L;

import butterknife.ButterKnife;


public class WelcomeActivity extends AppCompatActivity {

    /*序列号数据，用户信息，序列号、登录名、密码*/
    private String registerDataStr, loginDataStr, register, loginName, passWord;

//        public static String IpNum="198";
        public static String IpNum="99"; //6688  8666
//    public static String IpNum = "1002";
//    public static String IpNum = "8666";

    private FaceAuth faceAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initData();
//        setAuth();
        getIp();
    }

    void initData() {
        registerDataStr = SPUtils.getInstance().getString(BaseConstant.REGISTERDATA, "");
//        loginDataStr = SPUtils.getInstance().getString(SpData.LOGINDATA, "");
        register = SPUtils.getInstance().getString(BaseConstant.REGISTER, null);
        loginName = SPUtils.getInstance().getString(BaseConstant.LOGINNAME, null);
        passWord = SPUtils.getInstance().getString(BaseConstant.PASSWORD, null);
    }

    void getIp() {
        GetAddressBySerialNumberReq req = new GetAddressBySerialNumberReq();
        req.serialNumber = IpNum;
        MyApp.getInstance().requestData2(this, req, new ipListener(), new ipErrorListener());
    }

    class ipListener implements Response.Listener<GetAddressBySerialNumberRsp> {

        @Override
        public void onResponse(GetAddressBySerialNumberRsp rsp) {
            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
                SPUtils.getInstance().put(BaseConstant.NUMBER, !TextUtils.isEmpty(rsp.data.ipAddress) ? rsp.data.ipAddress : GetData.URL_IP);
                getRegister();
            }
        }
    }

    class ipErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (TextUtils.isEmpty(registerDataStr))
                goActivity(RegisterActivity.class);
            else {
                ValidSerialKeyRsp serialKeyRsp = JSON.parseObject(registerDataStr, ValidSerialKeyRsp.class);
                if (serialKeyRsp.data == null) {
                    goActivity(RegisterActivity.class);
                } else
                    switch (serialKeyRsp.data.type) {
                        /* 1=测试用，2=正式用*/
                        case 1:
                            goActivity(RegisterActivity.class);
                            break;
                        case 2:
                            goActivity(TextUtils.isEmpty(loginDataStr) ? LoginActivity.class : MainActivity.class);
                            break;
                    }
            }
        }
    }

    void getRegister() {
        if (TextUtils.isEmpty(register)) {
            goActivity(RegisterActivity.class);
        } else {
            ValidSerialKeyReq req = new ValidSerialKeyReq();
            req.serialKey = register;
            req.machineCode = DeviceUtils.getAndroidID();
            MyApp.getInstance().requestData2(this, req, new registerListener(), new registerErrorListener());
        }
    }

    class registerListener implements Response.Listener<ValidSerialKeyRsp> {

        @Override
        public void onResponse(ValidSerialKeyRsp rsp) {
            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
                getLogin();
            } else {
                goActivity(RegisterActivity.class);
            }
        }
    }

    class registerErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (TextUtils.isEmpty(registerDataStr))
                goActivity(RegisterActivity.class);
            else {
                ValidSerialKeyRsp serialKeyRsp = JSON.parseObject(registerDataStr, ValidSerialKeyRsp.class);
                if (serialKeyRsp.data == null) {
                    goActivity(RegisterActivity.class);
                } else
                    switch (serialKeyRsp.data.type) {
                        /* 1=测试用，2=正式用*/
                        case 1:
                            goActivity(RegisterActivity.class);
                            break;
                        case 2:
                            goActivity(TextUtils.isEmpty(loginDataStr) ? LoginActivity.class : MainActivity.class);
                            break;
                    }
            }

        }
    }

    void getLogin() {
        LoginReq req = new LoginReq();
        req.loginName = loginName;
        req.passWord = passWord;
        MyApp.getInstance().requestData(WelcomeActivity.this, req, new loginListener(), new loginErrorListener());
    }

    class loginListener implements Response.Listener<LoginRsp> {

        @Override
        public void onResponse(LoginRsp rsp) {
            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
//                SPUtils.getInstance().put(SpData.LOGINDATA, JSON.toJSONString(rsp));
                goActivity(MainActivity.class);
            } else {
                goActivity(LoginActivity.class);

            }
        }
    }

    class loginErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            goActivity(LoginActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 跳转Activity
     */
    void goActivity(Class clz) {
        startActivity(new Intent(WelcomeActivity.this, clz));
        finish();
    }

    void setAuth() {
        String key = GlobalSet.getLicenseOnLineKey();
//        String key = SPUtils.getInstance().getString(BaseConstant.BAIDUKEY);
        if (TextUtils.isEmpty(key))
            return;
        faceAuth = new FaceAuth();
//         建议3288板子flagsThreads设置2,3399板子设置4
        faceAuth.setAnakinThreadsConfigure(2, 1);
        initLicenseOnLine(key);
    }

    // 在线鉴权
    private void initLicenseOnLine(final String key) {
        if (TextUtils.isEmpty(key)) {
            Toast.makeText(this, "序列号不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        faceAuth.initLicenseOnLine(this, key, new AuthCallback() {
            @Override
            public void onResponse(final int code, final String response, String licenseKey) {
                L.d("==>" + code);
                if (code == 0) {
                    GlobalSet.FACE_AUTH_STATUS = 0;
//                    // 初始化人脸
//                    FaceSDKManager.getInstance().initModel(WelcomeActivity.this);
//                    // 初始化数据库
//                    DBManager.getInstance().init(getApplicationContext());
//                    // 加载feature 内存
//                    FaceSDKManager.getInstance().setFeature();
                    GlobalSet.setLicenseOnLineKey(key);
                    GlobalSet.setLicenseStatus(GlobalSet.LICENSE_ONLINE);
                } else {
                    ToastUtils.showShort(code + "授权码无效");
                }
            }
        });
    }
}
