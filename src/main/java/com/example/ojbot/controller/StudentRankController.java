package com.example.ojbot.controller;

import com.example.ojbot.pojo.CommonResult;
import com.example.ojbot.pojo.dto.result.FinalResult;
import com.example.ojbot.service.rankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.02
 */
@RestController
public class StudentRankController {


    @Resource
    private rankService rankService;

    @GetMapping("/rank")
    public String rank(Integer id){
        //        RestTemplate restTemplate = restTemplateConfig.restTemplate()
        return rankService.rank(id);
    }

    @GetMapping("/rankQQ")
    public CommonResult<List<FinalResult>> rankQQ(@RequestParam("id") Integer id) {
        return  rankService.rankQQ(id);
    }

    @GetMapping("/rankQQGroup")
    public CommonResult<List<FinalResult>> rankQQGroup(@RequestParam("qid") String qid) {
        return  rankService.rankQQGroup(qid);
    }

}
