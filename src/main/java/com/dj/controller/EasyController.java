package com.dj.controller;

import com.alibaba.fastjson2.JSON;
import com.dj.dto.JsonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/easy")
public class EasyController {


    @PostMapping("/submit")
    public String submit(@RequestBody Map<String, Object> params) {
        System.out.println(JSON.toJSONString(params));
        return "提交成功";
    }

    @GetMapping("/combobox")
    public JsonResult<List<Map<String, String>>> combobox() {
        List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", i + "");
            map.put("text", "小" + i);
            list.add(map);
        }
        return JsonResult.success(list);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件为空");
        }

        try {
            // 设置上传文件存储目录
            String uploadDir = "C:/temp/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File serverFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(serverFile);

            return ResponseEntity.ok("文件上传成功: " + serverFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("文件上传失败: " + e.getMessage());
        }
    }
}
