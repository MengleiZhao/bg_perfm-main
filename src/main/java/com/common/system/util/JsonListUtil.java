package com.common.system.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonListUtil {
    /**
     * List<T> 转 json 保存到数据库
     */
    public static <T> String listToJson(List<T> ts) {
        String jsons = JSON.toJSONString(ts);
        return jsons;
    }

    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        String listTxt = JSONArray.toJSONString(jsonString);
        @SuppressWarnings("unchecked")
        List<T> ts = (List<T>) JSONArray.parseArray(listTxt, clazz);
        return ts;
    }


    /**
     * 11.Object对象转 List集合
     * @param object Object对象
     * @param clazz 需要转换的集合 传参例子：jsonList,User.class
     * @param <T> 泛型类
     * @return
     */
    public static <T> List<T> changeList(Object object,Class<T> clazz){
        try {
            //1.创建一个目标类型的集合对象，因为不能确定，所以，用泛型<T>
            List<T> result = new ArrayList<>();
            //2.判断 源集合对象的类型 是否是 目标集合对象的类型，如果是，则进行转换，如果不是，则返回一个空集合
            if (object instanceof List<?>){
                //2-1.将源集合对象 强转为 目标集合对象类型，在进行遍历，因为此时 源集合对象的类型 的数据还没转换成目标集合对象的数据，所以，用Object来接收
                for (Object o : (List<?>) object) {
                    String string = JSONObject.toJSONString(o);//将其中的一条数据转换成json格式的字符串
                    T t = JSONObject.parseObject(string, clazz);//再将这条数据转换成目标类型
                    result.add(t);//将转换后的一条数据 添加到目标集合对象中
                }
                return result;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
