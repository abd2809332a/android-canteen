package com.example.android_canteen.requestbean;

import com.alibaba.fastjson.TypeReference;
import com.example.android_canteen.responbean.FoodtodayRsp;

/**
 * Created by Hao on 2018/5/3.
 */

public class FoodtodaychucReq extends BaseBeanReq<FoodtodayRsp> {

    //    officeId	是	string	学校ID
    //    classesId	是	string	班级ID
    //    moralEduList	是	Array	评分数组（json字符串数组）
    //            　id	是	string	评分项ID
    //　score	是	string	分数

    public int officeId;
    public String roomId ;
    public int orderId;


    @Override
    public String myAddr() {
        return ":8777/api/order/outOrder";
    }

    @Override
    public TypeReference<FoodtodayRsp> myTypeReference() {
        return new TypeReference<FoodtodayRsp>() {
        };
    }
}
