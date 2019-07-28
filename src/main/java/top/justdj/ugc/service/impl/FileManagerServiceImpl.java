package top.justdj.ugc.service.impl;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.justdj.ugc.service.FileManagerService;

import java.io.InputStream;
import java.util.Date;


/**
 * Created by superren on 2017/11/25.
 */
@Slf4j
@Service
public class FileManagerServiceImpl implements FileManagerService {
    
    @Value("${aliyun.oss.endPiont}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    
    private final String bucketName = "justdj-ugc";
    
    @Override
    public String bomUploadFile(String fileName, InputStream data) {
        // 创建ClientConfiguration实例
        ClientConfiguration conf = new ClientConfiguration();
        // 设置OSSClient使用的最大连接数，默认1024
        conf.setMaxConnections(200);
        // 设置请求超时时间，默认50秒
        conf.setSocketTimeout(10000);
        // 设置失败请求重试次数，默认3次
        conf.setMaxErrorRetry(5);
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint.trim(), accessKeyId.trim(), accessKeySecret.trim(), conf);
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 上传文件流
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileName, data));
            if (ObjectUtils.allNotNull(result.getResponse())){
                log.info("上传结果 是否成功 {} 原因{}" ,result.getResponse().isSuccessful(),result.getResponse().getErrorResponseAsString());
            }
        } catch (Exception e) {
            log.error("", e);
            return "";
        }
        //关闭client
        ossClient.shutdown();
        return getUrl(fileName);
    }

    @Override
    public InputStream bomDownloadFile(String fileName) {

        // 创建ClientConfiguration实例
        ClientConfiguration conf = new ClientConfiguration();
        // 设置OSSClient使用的最大连接数，默认1024
        conf.setMaxConnections(200);
        // 设置请求超时时间，默认50秒
        conf.setSocketTimeout(10000);
        // 设置失败请求重试次数，默认3次
        conf.setMaxErrorRetry(5);
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint.trim(), accessKeyId.trim(), accessKeySecret.trim(), conf);


        OSSObject object = ossClient.getObject(bucketName, fileName);
        System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
        object.getObjectContent();

        return object.getObjectContent();
    }
    
    @Override
    public String getUrl(String key) {
        // 创建ClientConfiguration实例
        ClientConfiguration conf = new ClientConfiguration();
        // 设置OSSClient使用的最大连接数，默认1024
        conf.setMaxConnections(200);
        // 设置请求超时时间，默认50秒
        conf.setSocketTimeout(10000);
        // 设置失败请求重试次数，默认3次
        conf.setMaxErrorRetry(5);
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint.trim(), accessKeyId.trim(), accessKeySecret.trim(), conf);
        Date expiration = new Date(System.currentTimeMillis() +( 365L * 24   * 60 * 60 * 1000));
        return ossClient.generatePresignedUrl(bucketName, key, expiration).toString();
    }

}
