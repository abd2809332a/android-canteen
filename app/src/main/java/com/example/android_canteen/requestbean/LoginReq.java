package com.example.android_canteen.requestbean;


import com.alibaba.fastjson.TypeReference;
import com.example.android_canteen.responbean.LoginRsp;

/**
 * Created by Hao on 2017/3/11.
 */

public class LoginReq extends BaseBeanReq<LoginRsp> {

//    loginName	是	string	用户名
//    passWord	是	string	密码

    public String loginName;

    public String passWord;

    @Override
    public String myAddr() {
        return ":8777/api/login";
    }

    @Override
    public TypeReference<LoginRsp> myTypeReference() {
        return new TypeReference<LoginRsp>() {
        };
    }
}
