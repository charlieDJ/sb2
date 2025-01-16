package com.dj.controller;

import com.alibaba.fastjson2.JSON;
import com.dj.service.DemoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/demo")
public class DemoController {


    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private DemoService demoService;

    @RequestMapping("/getUserByName")
    public String getUserByName() {
        return demoService.getUserByName().toString();
    }



    @GetMapping("/projects")
    public Map<String, Object> getProjects(@RequestParam(required = false) String q, @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer rows) throws IOException {
        // 读取 JSON 文件
        Resource resource = resourceLoader.getResource("classpath:projects.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ProjectData projectData = objectMapper.readValue(resource.getInputStream(), ProjectData.class);
        List<Project> filteredProjects;
        if(StringUtils.hasText(q)){
            // 过滤数据
            filteredProjects = projectData.getRows().stream()
                    .filter(project -> project.getName().contains(q))
                    .collect(Collectors.toList());
        }else{
            filteredProjects = projectData.getRows();
        }

        // 分页处理
        int total = filteredProjects.size();
        int start = (page - 1) * rows;
        int end = Math.min(start + rows, total);
        List<Project> paginatedProjects = filteredProjects.subList(start, end);

        // 返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("rows", paginatedProjects);
        return result;
    }

    // 项目数据类
    static class ProjectData {
        private List<Project> rows;
        private int total;

        // getter 和 setter
        public List<Project> getRows() {
            return rows;
        }

        public void setRows(List<Project> rows) {
            this.rows = rows;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    // 项目类
    static class Project {
        private String name;
        private String projectNumber;

        // 构造函数，getter 和 setter 省略
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProjectNumber() {
            return projectNumber;
        }

        public void setProjectNumber(String projectNumber) {
            this.projectNumber = projectNumber;
        }
    }


}
