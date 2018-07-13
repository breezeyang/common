package com.breeze.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GroupConsumerDemo implements Runnable {

    private String topic;
    private String path;
    public GroupConsumerDemo(String topic, String path) {
        this.path=path;
        this.topic = topic;
    }
    public static void main(String[] args) {
        GroupConsumerDemo gdkast=new GroupConsumerDemo("test", "data");
        new Thread(gdkast).start();
    }
    @Override
    public void run() {
        System.out.println("start runing consumer");

        Properties properties = new Properties();
        properties.put("zookeeper.connect", "10.94.34.55:8181");// 声明zk
        properties.put("group.id", "CMMtest");// 必须要使用别的组名称，// 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据
        properties.put("auto.offset.reset", "largest");
        ConsumerConnector consumer = Consumer
                .createJavaConsumerConnector(new ConsumerConfig(properties));

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1); // 一次从主题中获取一个数据
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer
                .createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);// 获取每次接收到的这个数据
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()) {
            String message = new String(iterator.next().message());
            //这里指的注意，如果没有下面这个语句的执行很有可能回从头来读消息的
            consumer.commitOffsets();
            System.out.println(message);
        }
    }
}
