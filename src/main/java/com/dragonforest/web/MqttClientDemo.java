package com.dragonforest.web;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Mqtt 客户端
 *
 * 测试订阅和发布
 */
public class MqttClientDemo implements MqttCallback {
    String topic = "MQTT";
    String content = "Message from MqttPublishSample";
    int qos = 2;
    String broker = "tcp://localhost:1883"; //本机地址
    String clientId = "JavaSample";
    MemoryPersistence persistence = new MemoryPersistence();


    private MqttClient sampleClient = null;
    private MqttConnectOptions connOpts = null;

    public MqttClientDemo() {
        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(this);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发布消息
     *
     * 测试步骤：
     * 1.cmd窗口输入 ： mosquitto_sub -d -t MQTT   启动控制台监听
     * 2.程序调用publish()方法，即可在控制台看见发布的消息
     *
     * @param topic
     * @param content
     */
    public void publish(String topic, String content) {
        try {
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    /**
     * 订阅消息
     *
     * 测试步骤：
     * 1.程序运行subscribe()方法进行监听等待
     * 2.cmd输入：mosquitto_pub -d -t MQTT -m "this is a message"  即可在程序控制台看到发送的消息
     *
     * @param topic
     */
    public void subscribe(String topic) {
        try {
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            sampleClient.subscribe(topic, 2);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    // 回调方法

    public void connectionLost(Throwable throwable) {
        System.out.println("connectionLost " + throwable.getMessage());
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("messageArrived " + mqttMessage.toString());
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            System.out.println("deliveryComplete " + iMqttDeliveryToken.getMessage().toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        MqttClientDemo mqttClientDemo = new MqttClientDemo();
        // 接收
//        mqttClientDemo.subscribe("MQTT");

        // 发送
        mqttClientDemo.publish("MQTT", "how are you?");
    }
}
