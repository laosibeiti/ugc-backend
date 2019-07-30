docker network create -d overlay ugc_online

--------------

docker rmi $(docker images -q) -f

docker service create --name ugc_online \
--network ugc_online \
--endpoint-mode vip \
-p 9009:9002 \
--replicas 2 \
registry.cn-shanghai.aliyuncs.com/justdj/ugc_backend_online:1.1.0

docker service create --name ugc_test \
--network ugc_test \
--endpoint-mode vip \
-p 9002:9002 \
--replicas 2 \
registry.cn-shanghai.aliyuncs.com/justdj/ugc_backend_test:1.0.0