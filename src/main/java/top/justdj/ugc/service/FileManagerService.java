package top.justdj.ugc.service;


import java.io.InputStream;

/**
 * 文件管理服务
 * Created by superren on 2017/11/25.
 */
public interface FileManagerService {

    /**
     * 上传文件到阿里云OSS
     * @param fileName
     * @param data
     * @return
     */
    public String bomUploadFile(String fileName, InputStream data);


    /**
     * 下载指定文件
     * @param fileName
     * @return
     */
    public InputStream bomDownloadFile(String fileName);

    

    String getUrl(String fileName);

}
