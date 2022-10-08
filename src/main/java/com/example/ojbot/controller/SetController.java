package com.example.ojbot.controller;

import com.example.ojbot.pojo.CommonResult;
import com.example.ojbot.pojo.dto.param.RootUrlParam;
import com.example.ojbot.service.SetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.10.03
 */
@RestController
public class SetController {

    @Resource
    private SetService service;

    @PostMapping("/setStudents")
    public CommonResult<String> setStudents(@RequestParam(value = "qqGroup") String qqGroup, @RequestBody Object groupStudents) {
        return service.setStudents(qqGroup, groupStudents);
    }

    @PostMapping("/setProblem")
    public CommonResult<String> setProblem(@RequestParam(value = "qqGroup") String qqGroup, @RequestBody Object groupProblems) {
        return service.setProblem(qqGroup, groupProblems);
    }

    @PostMapping("/setRoot")
    public CommonResult<String> setRoot(@RequestParam(value = "qqGroup") String qqGroup, @RequestBody RootUrlParam root) {
        return service.setRoot(qqGroup, root);
    }

}
