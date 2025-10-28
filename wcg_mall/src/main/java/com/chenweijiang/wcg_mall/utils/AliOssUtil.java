package com.chenweijiang.wcg_mall.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云oss工具类
 * @author 枳枳
 */
@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private  String bucketName;

    public String upload(String objectName, InputStream inputStream){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        //公文访问地址
        String url = "";
        try {
            // 创建存储空间。
            ossClient.createBucket(bucketName);  // 创建存储空间
            ossClient.putObject(bucketName, objectName, inputStream);  // 上传文件
            url = "https://"+bucketName+"."+endpoint.substring(endpoint.lastIndexOf("/")+1)+"/"+objectName;  // 获取文件的公网访问地址
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();  // 关闭OSSClient
            }
        }
        return url;  // 返回文件的公网访问地址
    }
}
