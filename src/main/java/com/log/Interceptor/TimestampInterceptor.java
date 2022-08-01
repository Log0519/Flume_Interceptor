package com.log.Interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author Log
 * @Date 2022/7/28 20:05
 * TODO
 */
public class TimestampInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //需求：修改headers中的时间戳值，改为日志数据中的时间，解决日志消费零点漂移问题
        //提供给hdfs Sink 使用，控制输出文件的文件夹名称
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(log);
        String timestamp = jsonObject.getString("ts");

        //获取headers
        Map<String, String> headers = event.getHeaders();
        headers.put("timestamp",timestamp);

        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        
        return events;
    }

    @Override
    public void close() {

    }
    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TimestampInterceptor();
        }

        @Override
        public void configure(Context context) {


        }
    }
}
