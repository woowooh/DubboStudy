# DubboStudy

install RPC api
docker 容器部署
consumer 一个
provider 两个

# 问题 1：docker 两个虚拟机间通信(consumer 请求 provider)
# 问题 2：dubbo 如何负载均衡多个 provider？


# dubbo Client，"漂亮"的拿到 类名，方法名，参数 等一系列信息， RPC 发送至 server
# dubbo Server，反序列化后，拿到被代理的 类，方法，参数