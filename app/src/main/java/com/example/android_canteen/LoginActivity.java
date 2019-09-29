package com.example.android_canteen;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.util.SPUtils;
import com.example.android_canteen.base.BaseConstant;
import com.example.android_canteen.requestbean.LoginReq;
import com.example.android_canteen.responbean.LoginRsp;
import com.example.android_canteen.utils.LoadingTools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    SweetAlertDialog pd;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.logo)
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pd = new LoadingTools().pd(this);
        post=findViewById(R.id.post);
        post.setOnClickListener(this);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
//        titie.setText(SpData.User().data.teachName);
//        titie.setBackgroundResource(R.drawable.fk_titile);
    }

    @OnClick(R.id.post)
    public void onViewClicked() {
//        startActivity(new Intent(this, Ma inActivity.class));
        if (TextUtils.isEmpty(et1.getText())) {
            Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et2.getText())) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else PostData();

//        finish();
    }

    void PostData() {
        pd.show();
        LoginReq req = new LoginReq();
        try {
            req.loginName = URLEncoder.encode(et1.getText().toString().trim(), "UTF-8");
            req.passWord = URLEncoder.encode(et2.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        MyApp.getInstance().requestData(this, req, new sListener(), new Error());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post:
                if (et1.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                } else if (et2.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else PostData();

                break;
        }
    }


    class sListener implements Response.Listener<LoginRsp> {

        @Override
        public void onResponse(LoginRsp rsp) {
            Log.e("TAG", "LoginRsp: " + JSON.toJSON(rsp));
            pd.dismiss();
            if (rsp.status == BaseConstant.REQUEST_SUCCES) {
                SpDate.officeId=rsp.data.officeId;
                SpDate.teachName=rsp.data.teachName;
                SpDate.officeName=rsp.data.officeName;
                SpDate.logo=rsp.data.logo;
                SpDate.teachBuildId=rsp.data.teachBuildId;
                SpDate.roomId=rsp.data.roomId;
                SPUtils.getInstance().put(BaseConstant.LOGINNAME, et1.getText().toString());
                SPUtils.getInstance().put(BaseConstant.PASSWORD, et2.getText().toString());
                SPUtils.getInstance().put("LOGINDATA", JSON.toJSONString(rsp));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Log.e("TAG", "SPUtils: " + JSON.toJSON(rsp));
//                L.j(JSON.toJSONString(rsp.data.cardRight));
                LoginActivity.this.finish();
            } else
                Toast.makeText(LoginActivity.this, rsp.info, Toast.LENGTH_SHORT).show();
//                tips.setText(rsp.info);
        }
    }

    class Error implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            pd.dismiss();
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.NORMAL_TYPE)
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

}
