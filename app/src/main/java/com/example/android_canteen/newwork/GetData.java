package com.example.android_canteen.newwork;


import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.example.android_canteen.base.BaseConstant;
import com.example.android_canteen.requestbean.BaseBeanReq;
import com.example.android_canteen.utils.AuthcodeTwo;
import com.example.android_canteen.utils.L;
import com.example.android_canteen.utils.MyHashMap;
import com.example.android_canteen.utils.Object2Map;

import java.io.File;
import java.net.URLEncoder;

/**
 * Created by Hao on 16/9/6.
 */
public class GetData {

    public static final String URL_IP = "120.76.189.190";

    public static final String url2 = "http://120.76.189.190";
    public static final String url3 = "http://192.168.3.130";

    public static String BIp = ":800/yide_manager/";

    public final static int PORT3 = 9123;
    /**
     * 网络请求的解析的密匙key
     **/
    public final static String URL_KEY = "24ca8a8a8a8888439b926572b5fb6233fb";

    public static <T> String requestUrl(BaseBeanReq<T> bean) {

        try {

            MyHashMap map = new MyHashMap();

            map.putAll(Object2Map.object2Map(bean));

            String encryStr = AuthcodeTwo.authcodeEncode(map.toString(), URL_KEY);

            String urlRequest = url() + bean.myAddr() + "?input=" + URLEncoder.encode(encryStr, "UTF-8");

            L.d("JSON", urlRequest);

            L.d("JSON", JSON.toJSONString(bean));

            return urlRequest;

        } catch (Exception e) {

            return null;
        }

    }

    public static <T> String requestUrl3(BaseBeanReq<T> bean) {

        try {

            MyHashMap map = new MyHashMap();

            map.putAll(Object2Map.object2Map(bean));

            String encryStr = AuthcodeTwo.authcodeEncode(map.toString(), URL_KEY);

            String urlRequest = url() + bean.myAddr();

            L.d("JSON2", urlRequest);

            L.d("JSON2", JSON.toJSONString(bean));

            return urlRequest;

        } catch (Exception e) {

            return null;
        }

    }

    public static <T> String requestUrl2(BaseBeanReq<T> bean) {

        try {

            MyHashMap map = new MyHashMap();

            map.putAll(Object2Map.object2Map(bean));

            String encryStr = AuthcodeTwo.authcodeEncode(map.toString(), URL_KEY);

            String urlRequest = url2 + bean.myAddr() + "?input=" + URLEncoder.encode(encryStr, "UTF-8");

            L.d("JSON", urlRequest);

            L.d("JSON", JSON.toJSONString(bean));

            return urlRequest;

        } catch (Exception e) {

            return null;

        }

    }

    public static <T> String requestFaceUrl(BaseBeanReq<T> bean) {

        try {

            MyHashMap map = new MyHashMap();

            map.putAll(Object2Map.object2Map(bean));

            String encryStr = AuthcodeTwo.authcodeEncodeFace(map.toString(), URL_KEY);

            String urlRequest = url() + bean.myAddr() + "?input=" + URLEncoder.encode(encryStr, "UTF-8");

            L.d("JSON", urlRequest);

            L.d("JSON", JSON.toJSONString(bean));

            return urlRequest;

        } catch (Exception e) {

            return null;
        }

    }

    public static <T> String request190(BaseBeanReq<T> bean) {

        try {

            MyHashMap map = new MyHashMap();

            map.putAll(Object2Map.object2Map(bean));

            String encryStr = AuthcodeTwo.authcodeEncodeFace(map.toString(), URL_KEY);

            String urlRequest = "http://120.76.189.190" + bean.myAddr() + "?input=" + URLEncoder.encode(encryStr, "UTF-8");

            L.d("JSON", urlRequest);

            L.d("JSON", JSON.toJSONString(bean));

            return urlRequest;

        } catch (Exception e) {

            return null;
        }

    }

    public static String url() {
        return "http://" + SPUtils.getInstance().getString(BaseConstant.NUMBER, URL_IP);
    }

    public static String faceUrl() {
        String port = SPUtils.getInstance().getString(BaseConstant.NUMBER, GetData.URL_IP);
        if (port.contains(":")) {
            String[] ip = port.split(":");
            return "http://" + ip[0]+ File.separator;
        } else return url2+ File.separator;
    }

    public static String imageUrl() {
        return "http://" + SPUtils.getInstance().getString(BaseConstant.NUMBER, URL_IP) + "/";
    }

    public static String YydUrl() {
//        Log.e("TAG", "YydUrl: "+ SPUtils.getInstance().getString(BaseConstant.NUMBER));
        return "http://" + SPUtils.getInstance().getString(BaseConstant.NUMBER, URL_IP) + BIp;
    }

    public static String uploadUrl() {
        return "http://" + SPUtils.getInstance().getString(BaseConstant.NUMBER, URL_IP) + "/" + "/upload.html";
    }
}
