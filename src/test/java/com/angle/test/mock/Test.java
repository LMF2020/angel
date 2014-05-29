package com.angle.test.mock;

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

        for (int i = 0; i < 10; i++) {
            System.out.println(IdGenerator.getId());
        }
    }
}
