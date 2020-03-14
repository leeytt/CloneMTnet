package com.leeyunt.clonemtnet;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FastJsonTest {

    @Test
    public void test1() {
        /**
         * 将对象转换成Json串：
         * JSON.toJSONString(对象)
         * */
        List<String> stringList = new ArrayList<>();
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");
        System.out.println(stringList);
        String jsonString = JSON.toJSONString(stringList);
        System.out.println(jsonString);

        /**
         * 将Json串转换成java对象：
         * //对象是单个元素
         * JSON.parseObject(json串，CLAZZ);
         * //对象包含多个元素
         * JSON.parseArray(json串，CLAZZ)；
         * */
        List<String> javaObject = JSON.parseArray(jsonString,String.class);
        System.out.println(javaObject);
    }
}
