package com.example.android_canteen.responbean;

/**
 * Created by Hao on 2017/3/11.
 */

public class LoginRsp {
    /**
     * status : 10000
     * info : 请求成功
     * data : {"officeName":"测试001","teachName":"","officeId":16790,"logo":"http://192.168.3.130/upload/201908/20/1566311258772072583.jpg","teachBuildId":"","roomId":"22c2453a926f4941b3c8da18fa7c0397"}
     */
    public int status;
    public String info;
    public DataBean data;

    public static class DataBean {
        /**
         * officeName : 测试001
         * teachName :
         * officeId : 16790
         * logo : http://192.168.3.130/upload/201908/20/1566311258772072583.jpg
         * teachBuildId :
         * roomId : 22c2453a926f4941b3c8da18fa7c0397
         */
        public String officeName;
        public String teachName;
        public int officeId;
        public String logo;
        public String teachBuildId;
        public String roomId;
    }
}
