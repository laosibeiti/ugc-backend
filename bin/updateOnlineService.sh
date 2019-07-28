docker service update \
--image registry.cn-shanghai.aliyuncs.com/justdj/ugc_backend:stable \
--update-parallelism 1 \
--update-delay 10s  ugc_online