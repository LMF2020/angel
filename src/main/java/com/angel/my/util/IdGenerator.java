package com.angel.my.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机id生成器
 */
public class IdGenerator {
    public static String getId(){
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++)
            result = result * 10 + array[i];
        //System.out.println(result);
        //System.out.println( System.nanoTime());//系统纳秒
        //System.out.println(UUID.randomUUID().toString());//不会重复的id
        return  new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+result;
    }
}
