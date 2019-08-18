package com.sceon.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/16 15:50
 */
@Service
public class TencentCloudProvider {
    @Autowired
    private TencentCloudProvider tencentCloudProvider;
    @Value("${tencentcloud.SecretId}")
    private String secretId;
    @Value("${tencentcloud.SecretKey}")
    private String secretKey;
    @Value("${tencentcloud.bucket}")
    private String bucket;
    @Value("${tencentcloud.region}")
    private String regionName;
    @Value("${tencentcloud.expires}")
    private Long expires;
    public String upload(MultipartFile multipartFile){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        String generateFileName="";
        String[] filePaths = multipartFile.getOriginalFilename().split("\\.");
        if(filePaths.length > 1){
            generateFileName= UUID.randomUUID().toString() + "." +filePaths[filePaths.length-1];
        }else {
            return null;
        }
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, generateFileName, multipartFile.getInputStream(),metadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        } catch (CosServiceException  serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回图片的url
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucket, generateFileName, HttpMethodName.GET);
        // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + expires);
        req.setExpiration(expirationDate);
        URL url = cosClient.generatePresignedUrl(req);
        //System.out.println(url.toString());
        return url.toString();
    }

}
