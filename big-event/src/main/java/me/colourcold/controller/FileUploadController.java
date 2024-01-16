package me.colourcold.controller;

import me.colourcold.pojo.Result;
import me.colourcold.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

//    @PostMapping
//    public Result upload(MultipartFile file) throws IOException {
//        String originalFilename = file.getOriginalFilename();
//        UUID uuid = UUID.randomUUID();
//        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\file\\" + uuid + suffix));
//        return Result.success("上传成功");
//    }

    @PostMapping
    public Result upload(MultipartFile file, String key, String secret) throws Exception {
        String originalFilename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = uuid + suffix;
        InputStream inputStream = file.getInputStream();
        String url = AliOssUtil.upload(objectName, inputStream, key, secret);
        return Result.success(url);
    }
}
