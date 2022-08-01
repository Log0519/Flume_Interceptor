package com.log.Interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Log
 * @Date 2022/7/28 15:05
 * TODO
 */
public class ETLInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //要求：过滤event中的数据是否是json格式
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);

        //解耦
        boolean flag=JSONUtils.isJson(log);
        return flag?event:null;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        //将处理过之后为null的event删除掉
        //使用迭代器
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()){
            Event event = iterator.next();
            if (intercept(event)==null) {
                iterator.remove();
            }
        }

        return events;
    }

    @Override
    public void close() {

    }
    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}