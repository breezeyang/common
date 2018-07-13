package com.breeze.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

public class RecipesDistAtomicInt {
    
    static String path = "/curator_recipes_atomicint_path";

    static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("10.95.45.37:8992")
            .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
    
    
    public static void main(String[] args) throws Exception {
        
        client.start();
        
        DistributedAtomicInteger atomicInt = new DistributedAtomicInteger(client, path, new RetryNTimes(1000, 3));
        AtomicValue<Integer> rc = atomicInt.add(8);
        Integer postValue = rc.postValue();
        System.out.println("postValue: " + postValue);
        System.out.println("result: " + rc.succeeded());
        client.close();
    }


}
