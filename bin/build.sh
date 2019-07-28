#!/bin/sh
#打包
echo "开始构建docker镜像"
mvn package docker:build -Dmaven.test.skip=true
#上传到docker服务器
sudo docker login --username=13588224627 registry.cn-qingdao.aliyuncs.com
imageslist=`docker images`
var1=`echo $imageslist|awk -F ' ' '{print $9}'`
sudo docker tag $var1 registry.cn-qingdao.aliyuncs.com/justdj/umbrella:latest
sudo docker push registry.cn-qingdao.aliyuncs.com/justdj/umbrella:latest