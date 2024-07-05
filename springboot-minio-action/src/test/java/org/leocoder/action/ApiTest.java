package org.leocoder.action;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.leocoder.action.oss.MinioConfiguration;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-05 00:36
 * @description :
 */
@SpringBootTest(classes = MinioApplication.class)
@Slf4j
public class ApiTest {


    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfiguration minioConfiguration;


    @Test
    public void test01() throws Exception {
        // 上传 文件名，使用 UUID 随机
        String file = "/Users/leo/Pictures/Leo图库/个人/avatar.jpg";
        String path = file.substring(file.lastIndexOf("/") + 1);
        minioClient.uploadObject(UploadObjectArgs.builder()
                // 存储桶
                .bucket(minioConfiguration.getBucket())
                // 文件名
                .object("qq.jpg")
                .filename(path)
                .build());

        // 获取访问路径
        log.info("文件上传成功，访问路径为：{}",String.format("%s/%s/%s", minioConfiguration.getEndpoint(), minioConfiguration.getBucket(), path));
    }
}
