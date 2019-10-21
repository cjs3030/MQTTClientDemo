#MQTT协议的通信Demo

## 1.服务端
* 1.下载 Mosquitto <br>
https://mosquitto.org/download/
根据自己的系统进行下载，我的是windows64版本

* 2.安装 <br>
傻瓜式安装,下一步

* 3.配置端口 <br>
找到安装目录下的 mosquitto.conf 中的 bind_address节点下port 1883注释放开，默认使用1883

* 4.配置环境变量 <br>
将Mosquitto安装目录放到环境变量里（略）

* 5.启动服务 <br>
安装完成之后，会添加一个Mosquitto Broker的系统服务，找到并开启服务

## 2.客户端（两种类型）

### Mosquitto自带命令行客户端（cmd命令行）

* 订阅消息： mosquitto_sub -d -t topic1 
* 发布消息： mosquitto_pub -d -t topic1 -m "Hello MQTT"


### 集成Paho依赖的程序客户端
见项目 MqttClientDemo.java

## 测试
* 测试使用cmd命令行方式和程序互为发送和接收端，二者角色颠倒。
### 订阅测试
    1.首先使用命令行方式订阅 mosquitto_sub -d -t topic1
    2.在程序中调用publish方法，注意传入相同的topic,这时控制台可观察到接收的消息

### 发布测试
    1.在程序中调用subscribe()方法.这时程序阻塞在这里。
    2.在命令行中进行发布消息： mosquitto_pub -d -t topic1 -m "Hello MQTT"  注意使用相同的topic，这时程序的控制台可观察到接收的消息
