package com.breeze.kafka;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.IOException;
import java.util.Properties;

public class ProducerDemo {

    public static void main(String[] args) {
        ProducerDemo s = new ProducerDemo();
        try {
            s.send("test", "breeze", "rose");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String topic, String key, String data) throws IOException {
        Properties props = new Properties();
        props.put("metadata.broker.list", "10.94.34.55:8092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // key.serializer.class默认为serializer.class
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);

        kafka.javaapi.producer.Producer producer = new kafka.javaapi.producer.Producer(config);
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.send(new KeyedMessage<String, String>(topic, key, data + i));

        }

        producer.close();
    }
}
