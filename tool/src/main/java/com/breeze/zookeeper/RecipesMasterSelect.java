package com.breeze.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class RecipesMasterSelect {

    static String master_path = "/curator_recipes_master_path";

    static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("10.95.45.37:8992")
            .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();

    public static void main(String[] args) throws InterruptedException {
        client.start();

        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {

            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为master角色");
                Thread.sleep(100);
                List<String> path = client.getChildren().forPath(master_path);
                System.out.println(path);
                System.out.println("完成master操作，释放master权利");
            }
        });

        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
        selector.close();
    }

}
