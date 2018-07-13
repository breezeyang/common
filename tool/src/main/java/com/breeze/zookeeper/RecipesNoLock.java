package com.breeze.zookeeper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class RecipesNoLock {

    public static void main(String[] args) {
        final CountDownLatch count = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        count.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号是:" + orderNo);
                }
            }).start();
        }
        count.countDown();

    }

}
