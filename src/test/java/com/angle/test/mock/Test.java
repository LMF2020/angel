package com.angle.test.mock;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-5-17
 * Time: 下午12:52
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        //System.out.println("11222");
        //List l = new ArrayList();
        //System.out.println(System.nanoTime());
//        for (int i = 0; i < 10; i++) {
//            //System.out.println(System.currentTimeMillis());
//            System.out.println(UUID.randomUUID().toString());
//        }

       /* for (int i = 0; i < 10; i++) {
            System.out.println(IdGenerator.getId());
        }*/

        Map aa = new HashMap();
        aa.put("purchaser_code",000001);
        aa.put("rank_code",102001);
        aa.put("TNPV",28);

        Map ab = new HashMap();
        ab.put("purchaser_code",000001);
        ab.put("rank_code", 102001);
        ab.put("TNPV", 28);

        List srcList = new ArrayList();
        srcList.add(aa);
        srcList.add(ab);

        List destList = new ArrayList();

        for (int i = 0; i < srcList.size(); i++) {
            destList.add(srcList.get(i));
        }
        //Collections.copy(destList,srcList);

        //srcList.remove(0);
        srcList.clear();
        System.out.println(destList.size());


    }
}
