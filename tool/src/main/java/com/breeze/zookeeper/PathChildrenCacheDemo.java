package com.breeze.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class PathChildrenCacheDemo {

    protected static String path = "/zkbook";

    static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("10.95.45.37:8992")
            .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();

    public static void main(String[] args) throws Exception {

        client.start();

        PathChildrenCache cache = new PathChildrenCache(client, path, true);

        cache.start(StartMode.POST_INITIALIZED_EVENT);

        cache.getListenable().addListener(new PathChildrenCacheListener() {

            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("child_added, " + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("child_update, " + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("child_remove, " + event.getData().getPath());

                        break;
                    default:
                        break;
                }

            }
        });

        String forPath = client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        System.out.println(forPath);
        Thread.sleep(1000);
        String forPath2 = client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
        System.out.println(forPath2);
        Thread.sleep(1000);
        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);
        client.delete().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
        cache.close();

    }

}
