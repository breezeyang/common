package com.breeze.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

public class ZkPathsDemo {

    static String path = "/curator_zkpath";

    static String connectionString = "10.95.45.37:8992";

    static CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectionString)
            .retryPolicy(new ExponentialBackoffRetry(1000,3)).sessionTimeoutMs(15000).build();

    public static  void main(String[] args) throws  Exception {
        client.start();
        ZooKeeper zookeeper = client.getZookeeperClient().getZooKeeper();
        System.out.println(ZKPaths.fixForNamespace(path, "sub"));
        System.out.println(ZKPaths.makePath(path, "sub"));
        System.out.println(ZKPaths.getNodeFromPath(path + "/sub1"));
        ZKPaths.PathAndNode pn = ZKPaths.getPathAndNode(path+ "/sub1");
        System.out.println(pn.getPath());
        System.out.println(pn.getNode());

        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        System.out.println(zookeeper.getState());
        ZKPaths.mkdirs(zookeeper, dir1);
        ZKPaths.mkdirs(zookeeper, dir2);

        System.out.println(ZKPaths.getSortedChildren(zookeeper, path));
        ZKPaths.deleteChildren(zookeeper, path, true);


    }
}
