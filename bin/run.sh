#!/bin/sh
#打包
echo "关闭旧容器"
docker stop justdj-job
docker rm justdj-job
echo "开始构建docker镜像"
mvn package docker:build -Dmaven.test.skip=true
echo "运行"
docker run -d -p 9002:9002  --name justdj-job justdj/job:latest