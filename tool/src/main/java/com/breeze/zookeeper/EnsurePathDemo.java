package com.breeze.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.ZooKeeper;

public class EnsurePathDemo {

    static String path = "/curator_ensurepath";

    static String connectionString = "10.95.45.37:8992";

    static CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectionString)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).sessionTimeoutMs(15000).build();

    public static void main(String[] args) throws Exception {
        client.start();
        client.usingNamespace("zk-book");
        EnsurePath ensurePath = new EnsurePath(path);
        ensurePath.ensure(client.getZookeeperClient());
        ensurePath.ensure(client.getZookeeperClient());
        EnsurePath ensurePath2 = client.newNamespaceAwareEnsurePath("/c1");
        ensurePath2.ensure(client.getZookeeperClient());

    }
}
