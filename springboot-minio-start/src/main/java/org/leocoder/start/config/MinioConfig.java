package org.leocoder.start.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-03 11:10
 * @description :
 */
@Configuration
public class MinioConfig {

    //单例的，那么MinioClient对象有没有线程安全问题呢？答案是：没有线程安全问题
    @Bean
    public MinioClient minioClient() {
        //链式编程，构建MinioClient对象
        return MinioClient.builder()
                .endpoint("http://192.168.4.100:9000")
                .credentials("admin", "password")
                .build();
    }
}
