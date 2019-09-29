package com.example.android_canteen.requestbean;


import com.alibaba.fastjson.TypeReference;
import com.example.android_canteen.responbean.ValidSerialKeyRsp;


/**
 * Created by Hao on 2017/3/11.
 */

public class ValidSerialKeyReq extends BaseBeanReq<ValidSerialKeyRsp> {

    public String machineCode;
    public String serialKey;

    @Override
    public String myAddr() {
        return ":800/yide_manager/api/classesSignSerialKey/validSerialKey.html";
//        return "yide_manager/api/classesSignSerialKey/validSerialKey.html";
    }

    @Override
    public TypeReference<ValidSerialKeyRsp> myTypeReference() {
        return new TypeReference<ValidSerialKeyRsp>() {
        };
    }
}
