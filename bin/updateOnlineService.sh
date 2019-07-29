docker rmi $(docker images -q)

docker service update \
--image registry.cn-shanghai.aliyuncs.com/justdj/ugc_backend_online:1.0.1 \
--update-parallelism 1 \
--update-delay 10s  ugc_online