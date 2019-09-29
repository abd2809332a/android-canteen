package com.example.android_canteen.responbean;

import java.util.List;

public class DifferentpostListRsp {


    /**
     * status : 10000
     * info : 请求成功
     * data : {"orderDetailList":[{"id":20,"orderId":null,"foodName":"手撕包菜","image":"http://192.168.3.130:8000/static/campus/image/201909/26/4.jpg","price":600,"number":1}],"total":600,"orderId":19,"name":"李大","photo":"http://192.168.3.130/upload/201906/12/1560333527068046517.jpg","info":""}
     */

    public int status;
    public String info;
    public DataBean data;

    public static class DataBean {
        /**
         * orderDetailList : [{"id":20,"orderId":null,"foodName":"手撕包菜","image":"http://192.168.3.130:8000/static/campus/image/201909/26/4.jpg","price":600,"number":1}]
         * total : 600
         * orderId : 19
         * name : 李大
         * photo : http://192.168.3.130/upload/201906/12/1560333527068046517.jpg
         * info :
         */

        public int total;
        public int orderId;
        public String name;
        public String photo;
        public String info;
        public List<OrderDetailListBean> menuList;

        public static class OrderDetailListBean {
            /**
             * id : 20
             * orderId : null
             * foodName : 手撕包菜
             * image : http://192.168.3.130:8000/static/campus/image/201909/26/4.jpg
             * price : 600
             * number : 1
             */

            public int id;
            public Object orderId;
            public String foodName;
            public String image;
            public int price;
            public int number;
        }
    }
}
