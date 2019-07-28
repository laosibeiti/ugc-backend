#!/bin/sh
#打包
echo "开始构建docker镜像"
mvn package docker:build -Dmaven.test.skip=true
#上传到docker服务器
echo "请输入阿里云docker仓库密码"
docker login --username=13588224627 registry.cn-hangzhou.aliyuncs.com
#请选择上传仓库
echo "以下是镜像仓库列表"
echo "1.测试环境"
echo "2.正式环境"
echo "请输入对应数字,选择上传的镜像仓库:\c"
read input
imageslist=`docker images`
var1=`echo $imageslist|awk -F ' ' '{print $9}'`
case $input in
1)sudo docker tag $var1 registry.cn-hangzhou.aliyuncs.com/justdj/ugc_backend:latest
  sudo docker push registry.cn-hangzhou.aliyuncs.com/justdj/ugc_backend:latest;;
2)sudo docker tag $var1 registry.cn-hangzhou.aliyuncs.com/justdj/ugc_backend:stable
  sudo docker push registry.cn-hangzhou.aliyuncs.com/justdj/ugc_backend:stable;;
esac