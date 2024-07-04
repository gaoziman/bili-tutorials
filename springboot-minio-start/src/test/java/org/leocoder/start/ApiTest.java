package org.leocoder.start;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.leocoder.start.service.MinIOService;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-03 23:32
 * @description :
 */
@SpringBootTest(classes = MinioStartApplication.class)
public class ApiTest {

    @Resource
    private MinIOService minIOService;

    @Resource
    private MinioClient minioClient;

    @Test
    void contextLoads() {
        minIOService.testMinIOClient();
    }


    // ----- bucket操作测试 -----

    @Test
    void test01() throws Exception {
        boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket("leocoder")
                .build());
        System.out.println("leocoder目录是否存在：" + isBucketExists);
    }


    @Test
    void test02() throws Exception {
        String bucketName = "myfile2";
        boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isBucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } else {
            System.out.println("bucket已经存在，不需要创建.");
        }

        String policyJsonString = "{\"Version\" : \"2012-10-17\",\"Statement\":[{\"Sid\":\"PublicRead\",\"Effect\":\"Allow\",\"Principal\":{\"AWS\":\"*\"},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
        // 创建存储桶的时候，设置该存储桶里面的文件的访问策略，运行公开的读；
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJsonString)// json串，里面是访问策略
                .build());
    }


    /**
     * 列出所有的bucket
     *
     * @throws Exception
     */
    @Test
    void test03() throws Exception {
        List<Bucket> bucketList = minioClient.listBuckets();
        bucketList.forEach(bucket -> {
            System.out.println(bucket.name() + " -- " + bucket.creationDate());
        });
    }


    /**
     * 删除bucket
     *
     * @throws Exception
     */
    @Test
    void test04() throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket("myfile2")
                .build());
    }


    // ----- 文件操作测试 -----
    @Test
    void test05() throws Exception {
        File file = new File("/Users/leo/Pictures/Leo图库/个人/avatar.jpg");
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket("leocoder")
                .object("test.jpg")
                .stream(new FileInputStream(file), file.length(), -1)
                .build());
        System.out.println(objectWriteResponse);

        ObjectWriteResponse objectWriteResponse2 = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket("leocoder").object("test2.jpg")
                .filename("/Users/leo/Pictures/Leo图库/个人/14.jpg")
                .build());
        System.out.println(objectWriteResponse);
    }


    @Test
    void test07() throws Exception {
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket("myfile")
                .object("test.jpg")
                .expiry(3, TimeUnit.MINUTES)
                .method(Method.GET).build());
        System.out.println(presignedObjectUrl);
    }

    /**
     * 向minio服务器下载文件
     *
     * @throws Exception
     */
    @Test
    void test08() throws Exception {
        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket("leocoder"
                ).object("test.jpg")
                .build());

        System.out.println(getObjectResponse.transferTo(new FileOutputStream("/Users/leo/Pictures/Leo图库/个人/test666.jpg")));
    }

    @Test
    void test09() throws Exception {
        Iterable<Result<Item>> listObjects = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket("leocoder")
                .build());

        listObjects.forEach(itemResult -> {
            try {
                Item item = itemResult.get();
                System.out.println(item.objectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void test10() throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("leocoder")
                .object("test.jpg")
                .build());
    }
}
