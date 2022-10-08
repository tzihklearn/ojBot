package com.example.ojbot.utils;

import com.example.ojbot.controller.StudentRankController;
import com.example.ojbot.mapper.AllGroupMapper;
import com.example.ojbot.pojo.dto.AllGroup;
import com.example.ojbot.pojo.dto.param.GroupList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
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

    @Resource
    private AllGroupMapper allGroupMapper;

    @Scheduled(cron = "0 0 18 * * ?")
    public void schedule() {
//        studentRankController.rank();
//        Map<String, Integer> qGroup = groupList.getQGroup();

        log.info("定时发送");
        List<AllGroup> allGroups = allGroupMapper.selectList(null);

        for (AllGroup allGroup : allGroups) {
            if (allGroup.getGroupId() != null && allGroup.getGroupRoot() != null) {
                studentRankController.rank(allGroup.getGroupId());
            }
        }


    }

//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
//    public void schedule2() {
//        studentRankController.rank();
//
//        log.info("定时发送");
//    }

}
