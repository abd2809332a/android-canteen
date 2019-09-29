package com.example.android_canteen.requestbean;

import com.alibaba.fastjson.TypeReference;
import com.example.android_canteen.responbean.DifferentpostListRsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hao on 2018/5/3.
 */

public class DiffrrentpostListReq extends BaseBeanReq<DifferentpostListRsp> {

    //    officeId	是	string	学校ID
    //    classesId	是	string	班级ID
    //    moralEduList	是	Array	评分数组（json字符串数组）
    //            　id	是	string	评分项ID
    //　score	是	string	分数

    public int officeId;
    public String roomId ;
    public String userId;
    public int   type;
    public List<MenuListBean> menuList = new ArrayList<>();
    @Override
    public String myAddr() {
        return ":8777/api/order/submitCanteenOrder";
    }

    @Override
    public TypeReference<DifferentpostListRsp> myTypeReference() {
        return new TypeReference<DifferentpostListRsp>() {
        };
    }
    public static class MenuListBean {
        public int num;
        public String foodName;
        public int id;
    }
}
