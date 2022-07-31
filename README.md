# wrpc
## 基于Netty网络编程框架，实现一个简单的RPC框架

1. 使用Netty框架实现网络通信 √
2. 自定义消息格式 √
3. 设计RPC通信协议 √
4. 解决TCP半包、粘包问题 √
5. 实现Protobuf、 Kryo等多种序列化方式 √
6. 使用Zookeeper实现服务器注册与发现服务 √
7. 负载均衡策略的实现 √

## config.properties参数说明:

- serializer.algorithm--设置序列化方式,不设置默认为0,其中 0:java原生 1:json 2:protobuf 3:Kryo
- server.host--设置服务端host,不设置默认为127.0.0.1
- server.port--设置服务端port,不设置默认为8080
- messageType.request--设置请求消息类型,不设置默认为0
- messageType.response--设置响应消息类型,不设置默认为1
- zookeeper.serverAddress--设置zookeeper远程注册地址,不设置默认为127.0.0.1:2181
- schedule--设置负载均衡策略,不设置默认为0,0:随机加载 1:轮询加载
