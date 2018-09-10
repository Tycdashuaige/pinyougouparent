package com.pinyougou.shop.controller;

import com.pinyougou.utils.FastDFSClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tangyucong
 * @Title: UploadController
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/720:49
 */
@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file){

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");

            String path = fastDFSClient.uploadFile(file.getBytes(), extension);

            String url = FILE_SERVER_URL + path;

            return new Result(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传发生错误");
        }


    }
}
