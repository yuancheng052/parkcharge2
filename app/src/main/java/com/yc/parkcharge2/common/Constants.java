package com.yc.parkcharge2.common;

/**
 * Created by a on 2017/5/9.
 */

public class Constants {

    public static String SUCCESS="1";
    public static String ERROR="0";
    public static String ROLE_MRG = "0";
    public static String ROLE_NORMAL = "1";
    public static String REMOTE_INVOKE="remoteInvoke";
    public static String STATUS="status";
    public static String DATA="data";
    public static String MSG="msg";
    public static String NETWORK_ERROR="网络调用失败";
    //已上传
    public static String UPLOAD_SIGN="1";
    //未上传
    public static String UNUPLOAD_SING="0";

    //车辆类型S:小车 L:大车
    public static int CAR_TYPE_S=0;

    //初始密码
    public static String PWD="0000";

    //正则表达式-手机号
    public static String phonePattern="^1[34578]\\d{9}$";
}
