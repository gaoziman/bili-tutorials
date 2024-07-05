package org.leocoder.action.controller;

import io.minio.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.leocoder.action.common.Result;
import org.leocoder.action.domain.UserInfo;
import org.leocoder.action.oss.MinioConfiguration;
import org.leocoder.action.service.UserContractService;
import org.leocoder.action.service.UserImageService;
import org.leocoder.action.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * (t_user_info)表控制层
 *
 * @author Leo
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserInfoController {
    @Resource
    private MinioClient minioClient;


    @Resource
    private MinioConfiguration minioConfiguration;


    @Resource
    private UserInfoService userInfoService;


    @Resource
    private UserContractService userContractService;


    @Resource
    private UserImageService userImageService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable Integer id) {
        return Result.success(userInfoService.getById(id));
    }


    /**
     * 查询所有数据
     * @return 返回结果
     */
    @GetMapping("/list")
    public Result list() {
        return Result.success(userInfoService.userList());
    }


    /**
     * 更新用户
     * @param userInfo 用户信息
     * @return 更新结果
     */
    @PutMapping
    public Result updateUser(@RequestBody UserInfo userInfo){
        boolean update = userInfoService.updateById(userInfo);
        return update ? Result.success() : Result.fail();
    }


    /**
     * 上传头像
     * @param file 头像文件
     * @param id 用户id
     * @return 上传结果
     * @throws Exception  上传异常
     */
    @PostMapping(value = "/image")
    public Result image(MultipartFile file, @RequestParam(value = "id") Integer id) throws Exception {
        //xxxx.jpg
        String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String object = id+subfix;
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioConfiguration.getBucket())
                .object(object)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build()
        );
        System.out.println(objectWriteResponse);

        userImageService.saveOrUpdateUserContract(id, minioConfiguration.getBucket(), object);
        return Result.success();
    }


    /**
     * 上传合同
     * @param file 合同文件
     * @param id 用户id
     * @return 上传结果
     * @throws Exception  上传异常
     */
    @PostMapping(value = "/contract")
    public Result contract(MultipartFile file, @RequestParam(value = "id") Integer id) throws Exception {
        //1234.jpg
        String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String object = id+subfix;
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioConfiguration.getBucket())
                .object(object)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build()
        );
        System.out.println(objectWriteResponse);

        userContractService.saveOrUpdateUserContract(id, minioConfiguration.getBucket(), object);
        return Result.success();
    }


    /**
     * 文件下载
     *
     * @return
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable(value = "id") Integer id, HttpServletResponse response) throws Exception {

        UserInfo userInfo = userInfoService.getUserById(id);
        String bucket = userInfo.getUserContractDO().getBucket();
        String object = userInfo.getUserContractDO().getObject();
        //
        // 要想让浏览器弹出下载框，你后端要设置一下响应头信息
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(object, StandardCharsets.UTF_8));
        //
        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());
        getObjectResponse.transferTo(response.getOutputStream());
    }

}
