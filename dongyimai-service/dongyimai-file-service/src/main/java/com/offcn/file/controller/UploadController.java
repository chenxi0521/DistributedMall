package com.offcn.file.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenxi
 * @date 2021/11/5 21:13
 * @description
 */
@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;


    @RequestMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf(".")+1);

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
            String uploadFile = fastDFSClient.uploadFile(file.getBytes(), extName);
            String url = FILE_SERVER_URL + uploadFile;

            return new Result(true, StatusCode.OK, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "上传失败");
        }
    }
}
