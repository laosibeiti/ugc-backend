docker network create -d overlay ugc_online

docker rmi $(docker images -q) -f

docker service create --name ugc_online \
--network ugc_online \
--endpoint-mode vip \
-p 9002:9002 \
--replicas 3 \
registry.cn-shanghai.aliyuncs.com/justdj/ugc_backend_online:1.0.6