package org.leocoder.start.service;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-03 22:08
 * @description :
 */
@Service
public class MinIOService {

    @Resource
    private MinioClient minioClient;

    public void testMinIOClient() {
        System.out.println(minioClient);
    }
}
