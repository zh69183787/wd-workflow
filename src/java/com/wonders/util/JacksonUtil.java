package com.wonders.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2015/1/29
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class JacksonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonUtil() {

    }

    public static ObjectMapper getInstance(){

        return mapper;
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T convert(Object fromValue,Class<T> toValueType) {
        try {
            return mapper.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        HashMap<String,Object> hmParams=new HashMap<String,Object>();
        hmParams.put("id", "11");
        hmParams.put("name", "张三");
        hmParams.put("telephone", "");
        hmParams.put("mobile1", "");
        hmParams.put("date",new Date());
        TestUser user= mapper.convertValue(hmParams, TestUser.class);
        List<Map<String,TestUser>> l = new ArrayList<Map<String, TestUser>>();
        Map<String ,TestUser> map = new HashMap<String, TestUser>();
        Map<String ,TestUser> map2 = new HashMap<String, TestUser>();
        map.put("1",user);map.put("2",user);
        map.put("3",user);map.put("4",user);
        l.add(map);l.add(map2);
        String json= mapper.writeValueAsString(l);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(l));
        List r  = mapper.readValue(json,List.class);
        System.out.println();
        //TestUser test=JacksonMapper.convert(hmParams, TestUser.class);

        //User user=JacksonMapper.readValue("{\"id\":\"1\",\"name\":\"zhangsan\",\"telephone\":\"\"}",User.class);
        //String d=DateFormatUtil.timeFormat(DateFormatUtil.DATETIME_FORMAT, test.getInitiateTime());


        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
//        TestUser obj = mapper.readValue(json, TestUser.class);


    }
}
