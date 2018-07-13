package com.breeze.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Random;

public class CuratorBarrierDemo {

    static final String connection_string = "10.95.45.37:8992";

    static final int session_outtime = 5000; // ms


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connection_string)
                                .sessionTimeoutMs(session_outtime).retryPolicy(retryPolicy).build();
                        client.start();

                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, "/supper", 5);
                        Thread.sleep(1000 * (new Random()).nextInt(3));
                        System.out.println(Thread.currentThread().getName() + "已经准备ok");
                        barrier.enter();
                        System.out.println("同时开始执行.............");
                        Thread.sleep(1000 * (new Random()).nextInt(3));
                        System.out.println(Thread.currentThread().getName() + "运行完毕");
                        barrier.leave();
                        System.out.println("同时退出运行");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "t" + i).start();
        }
    }

}
