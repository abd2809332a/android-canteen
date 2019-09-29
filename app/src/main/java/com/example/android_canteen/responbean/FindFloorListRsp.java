package com.example.android_canteen.responbean;

import java.util.List;

public class FindFloorListRsp {


    /**
     * status : 10000
     * info : 请求成功
     * data : [{"name":"一层","id":"1"},{"name":"二层","id":"2"}]
     */

    public int status;
    public String info;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * name : 一层
         * id : 1
         */

        public String name;
        public String id;
    }
}
