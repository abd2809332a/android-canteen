package com.example.android_canteen.base;


import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;

/**
 * Created by Hao on 17/10/26.
 * 常量
 */
public class BaseConstant {
    /**
     * app打开次数
     */
    public static String openTimes = "OPENTIMES";
    /**
     * 分页加载数量
     */
    public static final int PAGE_SIZE = 20;

    /*请求成功消息编码*/
    public static int REQUEST_SUCCES = 10000;

    /*刷卡器接口*/
    public static String SPORT = "/dev/ttyS3";


    public static String SPORT2 = "/dev/ttyS4";

    public static String FILE_NAME = "FACE_TEMP.jpg";

    /*刷卡器波特率*/
    public static int IBAUDRATE = 9600;
    /*无感考勤波特率*/
    public static int IBAUDRATE2 = 115200;

    /*缓存*/
    public static String SP_NAME = "LANDSCAPE";

    /*注册码*/
    public static String REGISTER = "REGISTER";

    /*注册码信息*/
    public static String REGISTERDATA = "REGISTERDATA";

    /*百度授权码*/
    public static String BAIDUKEY = "BAIDUKEY";

    /*账号*/
    public static String LOGINNAME = "LOGINNAME";

    /*密码*/
    public static String PASSWORD = "PASSWORD";

    /*mina状态，注册*/
    public static String MINA_REG = "reg";

    /*mina状态，心跳*/
    public static String MINA_IDLE = "idle";

    /*mina状态，退出*/
    public static String MINA_EXIT = "exit";

    /*请求服务器地址*/
    public static String NUMBER = "NUMBER";
    public static String StopJs = "StopJs";
    public static String StartJs = "StartJs";
    public static String DOSHOMTHING = "DOSHOMTHING";
    public static String STUDENTID = "STUDENTID";
    public static String TEACHERID = "TEACHERID";
    public static String GROUPID = "GROUPID";


    //宿舍评分
    public static final int TOSCORE=123;
    //报警
    public static final int ToAlram=124;
    //学生空间
    public static final int ToUserCent=125;
    //水电使用
    public static final int ToUserwater=126;
    //保修
    public static final int ToUserbaoxiu=127;
    //通知
    public static final int Tousertongzhi=128;
    //物资清单
    public static final int Tousermater=129;
    //品牌注册APK
    public static boolean IsBrand = false;

    //华瑞安机器
    public static boolean HRA = false;

    /*素质评价,教师权限*/
    public static final int QUALITY = 101;
    /*学生空间,学生权限*/
    public static final int USERCENTER = 102;
    /*班级德育,学生权限*/
    public static final int MORAL = 103;
    /*设置,学生权限*/
    public static final int SETTING = 104;
    //素质评价————教师点评
    public static final int QUALITY_DP = 105;
    public static final int SelectCourses = 106;
    public static final int WIFI = 107;
    /*加入社团*/
    public static final int ADDCLUB = 108;
    //活动报名
    public static final int ADDACTIVITE = 109;
    public static final int ADDACTIVITE2 = 113;
    //投票
    public static final int TP = 110;
    //社团联系老师
    public static final int MSGTOTEACHER = 111;
    //物联控制权限
    public static final int WLKZCHECK = 112;
    //出行身份验证
    public static final int CHEACK = 119;
    public static final int CHEACKSTUDENT = 120;
    public static final int STUDENTGO = 121;
    public static final int STUDENTBACK = 123;


    //进设置
    public static final int TOSETTING = 122;
    public static int baseWidth = 620;
    public static int baseHeight = 309;
    //缓存首页排版
    public static String HOME_MODULE = "HOME_MODULE";
    //缓存第二页模板
    public static String HOME_MODULE2 = "HOME_MODULE2";
    //百度人脸db库
    public static String BD_PATH = "/data/data/" + AppUtils.getAppPackageName() + "/databases/";
    public static String BD_FEATURE_PATH = Environment.getExternalStorageDirectory() + "/BD_FEATURE/";
}
