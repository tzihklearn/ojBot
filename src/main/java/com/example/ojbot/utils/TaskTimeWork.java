package com.example.ojbot.utils;

import com.example.ojbot.controller.StudentRankController;
import com.example.ojbot.pojo.dto.param.GroupList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Component
@Slf4j
public class TaskTimeWork {

    @Resource
    private StudentRankController studentRankController;

    @Resource
    private GroupList groupList;

    @Scheduled(cron = "0 0 12,18,23 * * ?")
    public void schedule() {
//        studentRankController.rank();
        Map<String, Integer> qGroup = groupList.getQGroup();

        studentRankController.rank(1);
        log.info("定时发送");
    }

//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
//    public void schedule2() {
//        studentRankController.rank();
//
//        log.info("定时发送");
//    }

}
