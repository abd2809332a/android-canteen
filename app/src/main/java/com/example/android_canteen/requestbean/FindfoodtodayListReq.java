package com.example.android_canteen.requestbean;

import com.alibaba.fastjson.TypeReference;
import com.example.android_canteen.responbean.FindfoodtodayListRsp;

/**
 * Created by Hao on 2018/5/3.
 */

public class FindfoodtodayListReq extends BaseBeanReq<FindfoodtodayListRsp> {

    //    officeId	是	string	学校ID
    //    classesId	是	string	班级ID
    //    moralEduList	是	Array	评分数组（json字符串数组）
    //            　id	是	string	评分项ID
    //　score	是	string	分数

    public int officeId;
    public String roomId ;


    @Override
    public String myAddr() {
        return ":8777/api/dinner/getCanteenMenuList";
    }

    @Override
    public TypeReference<FindfoodtodayListRsp> myTypeReference() {
        return new TypeReference<FindfoodtodayListRsp>() {
        };
    }
}
