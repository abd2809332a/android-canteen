package com.example.android_canteen.requestbean;

import com.alibaba.fastjson.TypeReference;
import com.example.android_canteen.responbean.DifferentpostcxtRsp;

/**
 * Created by Hao on 2018/5/3.
 */

public class DiffrrentqccxReq extends BaseBeanReq<DifferentpostcxtRsp> {

    //    officeId	是	string	学校ID
    //    classesId	是	string	班级ID
    //    moralEduList	是	Array	评分数组（json字符串数组）
    //            　id	是	string	评分项ID
    //　score	是	string	分数

    public int officeId;
    public String roomId ;
    public String userId;
    public int   type;

    @Override
    public String myAddr() {
        return ":8777/api/order/takeOrder";
    }

    @Override
    public TypeReference<DifferentpostcxtRsp> myTypeReference() {
        return new TypeReference<DifferentpostcxtRsp>() {
        };
    }

}
