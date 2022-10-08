package com.example.ojbot.service;

import com.example.ojbot.pojo.CommonResult;
import com.example.ojbot.pojo.dto.param.RootUrlParam;
import com.example.ojbot.pojo.dto.param.StudentParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface SetService {

    CommonResult<String> setStudents(String qqGroup, Object groupStudents);

    CommonResult<String> setProblem(String qqGroup, Object groupProblems);

    CommonResult<String> setRoot(String qqGroup, RootUrlParam root);

}
