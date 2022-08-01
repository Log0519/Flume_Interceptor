package com.log.Interceptor;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author Log
 * @Date 2022/7/28 15:06
 * TODO
 */
public class JSONUtils {
    public static boolean isJson(String log){
        boolean flag =false;
        //判断log是否是json
        try{
            JSONObject.parseObject(log);
            flag=true;
        }catch(JSONException e){

        }
        return flag;
    }
}