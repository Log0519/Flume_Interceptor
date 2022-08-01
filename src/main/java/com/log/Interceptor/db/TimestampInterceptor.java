package com.log.Interceptor.db;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author Log
 * @Date 2022/8/1 9:36
 * TODO
 */
public class TimestampInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //需求：将event中的数据里面的时间戳拿出来
        //放入到headers中，提供给hdfs sink使用，控制输出的文件路径
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);

        JSONObject jsonObject = JSONObject.parseObject(log);
        Long ts = jsonObject.getLong("ts");
        String s = ts * 1000L + "";//转化为string
        Map<String, String> headers = event.getHeaders();
        headers.put("timestamp",s);
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
