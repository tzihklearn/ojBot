package com.example.ojbot.service;

import com.example.ojbot.pojo.CommonResult;
import com.example.ojbot.pojo.dto.result.FinalResult;

import java.util.List;

public interface rankService {

    String rank(Integer id);

    CommonResult<List<FinalResult>> rankQQ(Integer id);

    CommonResult<List<FinalResult>> rankQQGroup(String qid);
}
