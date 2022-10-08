package com.example.ojbot.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.ojbot.mapper.AllGroupMapper;
import com.example.ojbot.pojo.CommonResult;
import com.example.ojbot.pojo.dto.AllGroup;
import com.example.ojbot.pojo.dto.param.RootUrlParam;
import com.example.ojbot.service.SetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author tzih
 * @date 2022.10.03
 */
@Service
public class SetServiceImpl implements SetService {

    @Resource
    private AllGroupMapper allGroupMapper;

    @Override
    public CommonResult<String> setStudents(String qqGroup, Object groupStudents) {

        if (qqGroup.isEmpty()) {
            return CommonResult.fail("参数不得为空");
        }
//        System.out.println(groupStudents);

        JSONObject jsonObject = new JSONObject(groupStudents);

//        System.out.println("groupStudent\n"+groupStudent);
        System.out.println("qqGroup" + qqGroup);

        AllGroup id = allGroupMapper.selectOne(new UpdateWrapper<AllGroup>().eq("group_qq", qqGroup));

        System.out.println("id: " + id);

        AllGroup allGroup = new AllGroup();

        allGroup.setGroupMember(jsonObject.toString());

        int r;

        if (id == null) {

            allGroup.setGroupQq(qqGroup);

            r = allGroupMapper.insert(allGroup);

        }
        else {

            UpdateWrapper<AllGroup> updateWrapper = new UpdateWrapper<AllGroup>().set("group_member", jsonObject.toString()).eq("group_qq", qqGroup);

            r = allGroupMapper.update(null, updateWrapper);

        }

        if (r == 0) {
            return CommonResult.fail("请求失败");
        }
        else {
            return CommonResult.success();
        }

    }

    @Override
    public CommonResult<String> setProblem(String qqGroup, Object groupProblems) {
        if (qqGroup.isEmpty()) {
            return CommonResult.fail("参数不得为空");
        }

        System.out.println("qqGroup" + qqGroup);

        AllGroup id = allGroupMapper.selectOne(new UpdateWrapper<AllGroup>().eq("group_qq", qqGroup));

        System.out.println("id: " + id);

        JSONObject jsonObject = new JSONObject(groupProblems);

        AllGroup allGroup = new AllGroup();

        allGroup.setGroupProblem(jsonObject.toString());

        int r;

        if (id == null) {

            allGroup.setGroupQq(qqGroup);

            r = allGroupMapper.insert(allGroup);

        }
        else {

            UpdateWrapper<AllGroup> updateWrapper = new UpdateWrapper<AllGroup>().set("group_problem", jsonObject.toString() ).eq("group_qq", qqGroup);

            r = allGroupMapper.update(allGroup, updateWrapper);

        }

        if (r == 0) {
            return CommonResult.fail("请求失败");
        }
        else {
            return CommonResult.success();
        }
    }

    @Override
    public CommonResult<String> setRoot(String qqGroup, RootUrlParam root) {
        if (qqGroup.isEmpty()) {
            return CommonResult.fail("参数不得为空");
        }

        System.out.println("qqGroup" + qqGroup);

        AllGroup id = allGroupMapper.selectOne(new UpdateWrapper<AllGroup>().eq("group_qq", qqGroup));

        System.out.println("id: " + id);


        AllGroup allGroup = new AllGroup();
//        System.out.println("s:"+s);

        allGroup.setGroupRoot(root.getRoot());


        int r;

        if (id == null) {

            allGroup.setGroupQq(qqGroup);

            r = allGroupMapper.insert(allGroup);

        }
        else {

            UpdateWrapper<AllGroup> updateWrapper = new UpdateWrapper<AllGroup>().set("group_root", root.getRoot() ).eq("group_qq", qqGroup);

            r = allGroupMapper.update(allGroup, updateWrapper);

        }

        if (r == 0) {
            return CommonResult.fail("请求失败");
        }
        else {
            return CommonResult.success();
        }
    }
}
