docker service create --name ugc_online \
-p 9002:9002 \
--replicas 3 \
registry.cn-shanghai.aliyuncs.com/justdj/ugc_backend:stable