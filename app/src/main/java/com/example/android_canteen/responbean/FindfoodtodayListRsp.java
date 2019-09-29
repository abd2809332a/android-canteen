package com.example.android_canteen.responbean;

import java.util.List;

public class FindfoodtodayListRsp {


    /**
     * status : 10000
     * info : 请求成功
     * data : [{"image":"http://192.168.3.130:8000/static/campus/image/201909/26/8.jpg","price":1000,"name":"雪菜肉丝面","allowance":50,"id":16},{"image":"http://192.168.3.130:8000/static/campus/image/201909/26/1.jpg","price":1000,"name":"辣子鸡丁","allowance":50,"id":17},{"image":"http://192.168.3.130:8000/static/campus/image/201909/26/2.jpg","price":500,"name":"西红柿鸡蛋","allowance":50,"id":18},{"image":"http://192.168.3.130:8000/static/campus/image/201909/26/3.jpg","price":600,"name":"苦瓜","allowance":50,"id":19},{"image":"http://192.168.3.130:8000/static/campus/image/201909/26/7.jpg","price":1000,"name":"鸡蛋面","allowance":50,"id":20}]
     */

    public int status;
    public String info;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * image : http://192.168.3.130:8000/static/campus/image/201909/26/8.jpg
         * price : 1000
         * name : 雪菜肉丝面
         * allowance : 50
         * id : 16
         */

        public String image;
        public int price;
        public String name;
        public int allowance;
        public int id;
    }
}
