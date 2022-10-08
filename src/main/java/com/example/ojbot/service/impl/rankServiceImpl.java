package com.example.ojbot.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.ojbot.mapper.AllGroupMapper;
import com.example.ojbot.pojo.CommonResult;
import com.example.ojbot.pojo.dto.AllGroup;
import com.example.ojbot.pojo.dto.param.GroupList;
import com.example.ojbot.pojo.dto.param.ProblemList;
import com.example.ojbot.pojo.dto.param.UserList;
import com.example.ojbot.pojo.dto.result.FinalResult;
import com.example.ojbot.pojo.dto.result.Problem;
import com.example.ojbot.pojo.dto.result.ojResult;
import com.example.ojbot.service.rankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import java.util.*;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Service
public class rankServiceImpl implements rankService {

    @Resource(name = "httpClientTemplate")
    private RestTemplate restTemplate;



//    private static final RestTemplate restTemplate = new RestTemplate();

//    @Resource
//    private RestTemplateConfig restTemplateConfig;
    @Resource
    private UserList userList;

    @Resource
    private ProblemList problemList;

    @Resource
    private GroupList groupList;

    @Resource
    private AllGroupMapper allGroupMapper;

    @Override
    public String rank(Integer id) {

//        String url = "https://code.sipcoj.com/api/profile?username=";
//        Object forObject = restTemplate.getForObject(url, String.class);
//        System.out.println(forObject.toString());

//        HttpHeaders httpHeaders = new HttpHeaders();
//       httpHeaders.set("Cookie", "_ga=GA1.2.2048795898.1663412801; csrftoken=v7W9wLfNgUFB9wgVlsdhHnHSrWZLxBhThEVGi13tzG6g2pUGEp4Pd6e5eeVJ8OW3; sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal; _gid=GA1.2.2056050097.1664683520");
//        httpHeaders.set("Cookie" ,"sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal");

//        Set<String> students = new HashSet<>();
//        students.add("22-邱琬月");
//        students.add("22-何智博");
//        students.add("22-孙贤为");
//        students.add("22-李卓然");
//        students.add("22-王嘉泽");
//        students.add("22-赵滟清");
//        students.add("22-周加得");
        Map<String, Object> key = new HashMap<>();
        key.put("id", id);
        List<AllGroup> allGroups = allGroupMapper.selectByMap(key);
//        System.out.println(allGroups);
        if (allGroups == null) {
            return null;
        }
//        System.out.println(allGroups.toArray());
//        Integer id = groupList.getQGroup().get(qid);
        AllGroup allGroup = allGroups.get(0);
//        Integer id = allGroup.getGroupId();
//        if (id == null) {
//            return CommonResult.fail("没有该组");
//        }

//        Set<String> students = userList.getStudents().get(id);
        JSONObject jsonMember = new JSONObject(allGroup.getGroupMember());
        if (jsonMember.isEmpty()) {
            return null;
        }
        JSONArray groupMember = (JSONArray)jsonMember.get("name");
        List<String> students = groupMember.toList(String.class);
        if (students == null) {
            return null;
        }

//        Set<Integer> week = problemList.getProblems().get(id);
        JSONObject jsonProblem = new JSONObject(allGroup.getGroupProblem());
        if (jsonProblem.isEmpty()) {
            return null;
        }
        JSONArray groupProblem = (JSONArray)jsonProblem.get("problem");
        List<Integer> week = groupProblem.toList(Integer.class);
//        Set<String> students = userList.getStudents().get(1);
//
//
////        Set<Integer> week = new HashSet<>();
////
////        week.add(1);
////        week.add(2);
//
//        Set<Integer> week = problemList.getProblems().get(id);

        List<FinalResult> results = new ArrayList<>();
        for (String student : students) {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            String url = "https://code.sipcoj.com/api/profile?username=" + student;
            System.out.println(url);
//            HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

//            System.out.println(requestEntity);
            System.out.println( "1:  " + new Date().getTime());
//            ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET,
//                    JSONObject.class);
            JSONObject forObject = restTemplate.getForObject(url, JSONObject.class);

            System.out.println("2:   " + new Date().getTime());

//            System.out.println(exchange);
//            System.out.println(exchange.getBody());

            ojResult body = null;
//            if (exchange.toString().contains("User does not exist")) {
//                continue;
//            }
            if (forObject.toString().contains("User does not exist")) {
                continue;
            }

//            try {
//                body = objectMapper.readValue(Objects.requireNonNull(exchange.getBody()).toString(), ojResult.class);
//            }
//            catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }

            try {
                body = objectMapper.readValue(Objects.requireNonNull(forObject).toString(), ojResult.class);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            assert body != null;
            Map<Integer, Problem> problems = body.getData().getAcm_problems_status().getProblems();
            int i = 0;

            if (problems != null) {
                for (Integer pId : week) {
                    Problem problem = problems.get(pId);
                    if (problem != null)
                        ++i;
                }

                results.add(new FinalResult(body.getData().getUser().get("username"), i, week.size()));
            }
            else {
                results.add(new FinalResult(body.getData().getUser().get("username"), 0, week.size()));
            }
        }

        results.sort(((o1, o2) -> o2.getFinish() -o1.getFinish()));

        System.out.println(results);
        int i = 0;
        StringBuilder s = new StringBuilder();
        for (FinalResult result : results) {
            ++i;
            s.append(i+"."+result.getName() + "(" + result.getFinish() +"/" + result.getTotal() + ")" + "n");
        }
//        System.out.println(s.toString());
        return s.toString();
    }


    @Override
    public CommonResult<List<FinalResult>> rankQQ(Integer id) {

//        System.out.println(restTemplate.hashCode());

        Map<String, Object> key = new HashMap<>();
        key.put("group_id", id);
        List<AllGroup> allGroups = allGroupMapper.selectByMap(key);
//        System.out.println(allGroups);
        if (allGroups == null) {
            return CommonResult.fail("没有该组");
        }
//        System.out.println(allGroups.toArray());
//        Integer id = groupList.getQGroup().get(qid);
        AllGroup allGroup = allGroups.get(0);
//        Integer id = allGroup.getGroupId();
//        if (id == null) {
//            return CommonResult.fail("没有该组");
//        }

//        Set<String> students = userList.getStudents().get(id);
        JSONObject jsonMember = new JSONObject(allGroup.getGroupMember());
        if (jsonMember.isEmpty()) {
            return CommonResult.fail();
        }
        JSONArray groupMember = (JSONArray)jsonMember.get("name");
        List<String> students = groupMember.toList(String.class);
        if (students == null) {
            return CommonResult.fail("该组没有成员");
        }

//        Set<Integer> week = problemList.getProblems().get(id);
        JSONObject jsonProblem = new JSONObject(allGroup.getGroupProblem());
        if (jsonProblem.isEmpty()) {
            return CommonResult.fail();
        }
        JSONArray groupProblem = (JSONArray)jsonProblem.get("problem");
        List<Integer> week = groupProblem.toList(Integer.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        HttpHeaders httpHeaders = new HttpHeaders();
//       httpHeaders.set("Cookie", "_ga=GA1.2.2048795898.1663412801; csrftoken=v7W9wLfNgUFB9wgVlsdhHnHSrWZLxBhThEVGi13tzG6g2pUGEp4Pd6e5eeVJ8OW3; sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal; _gid=GA1.2.2056050097.1664683520");
//        httpHeaders.set("Cookie" ,"sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal");


//        Set<String> students = userList.getStudents().get(id);
//        if (students == null) {
//            return CommonResult.fail("没有该组");
//        }
//        Set<Integer> week = problemList.getProblems().get(id);
//        if (week == null) {
//            return CommonResult.fail("没有该组");
//        }
        List<FinalResult> results = new ArrayList<>();
        long t = new Date().getTime();
        for (String student : students) {
//            String url = "https://code.sipcoj.com/api/profile?username=" + student;
            String url = "https://code.sipcoj.com/api/profile?username={student}";

//            System.out.println(url);
//            HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

//            System.out.println(requestEntity);
            Long t1 = new Date().getTime();
            System.out.println( "1:  " + t1);
//            ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
//                    JSONObject.class);
            Map<String, String> map = new HashMap<>();
            map.put("student", student);
            Object forObject = restTemplate.getForObject(url, JSONObject.class, map);
            Long t2 = new Date().getTime();
            System.out.println("2:  " + t2);
            System.out.println( t2 - t1);

//            System.out.println(exchange);
//            System.out.println(exchange.getBody());
            ojResult body = null;
//            if (exchange.toString().contains("User does not exist")) {
//                continue;
//            }
            if (forObject.toString().contains("User does not exist")) {
                continue;
            }

//            try {
//                body = objectMapper.readValue(Objects.requireNonNull(exchange.getBody()).toString(), ojResult.class);
//            }
//            catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
            try {
                body = objectMapper.readValue(Objects.requireNonNull(forObject).toString(), ojResult.class);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            Map<Integer, Problem> problems = body.getData().getAcm_problems_status().getProblems();
            int i = 0;

            if (problems != null) {
                for (Integer pId : week) {
//                    System.out.println(new Date().getTime());
                    Problem problem = problems.get(pId);
                    if (problem != null)
                        ++i;
                }

                results.add(new FinalResult(body.getData().getUser().get("username"), i, week.size()));
            }
            else {
                results.add(new FinalResult(body.getData().getUser().get("username"), 0, week.size()));
            }
        }

        results.sort(((o1, o2) -> o2.getFinish() -o1.getFinish()));

        System.out.println(results);
        System.out.println(new Date().getTime() - t);
        return CommonResult.success(results);
    }


    @Override
    public CommonResult<List<FinalResult>> rankQQGroup(String qid) {

        Map<String, Object> key = new HashMap<>();
        key.put("group_qq", qid);
        List<AllGroup> allGroups = allGroupMapper.selectByMap(key);
//        System.out.println(allGroups);
        if (allGroups == null) {
            return CommonResult.fail("没有该组");
        }
//        System.out.println(allGroups.toArray());
//        Integer id = groupList.getQGroup().get(qid);
        AllGroup allGroup = allGroups.get(0);
//        Integer id = allGroup.getGroupId();
//        if (id == null) {
//            return CommonResult.fail("没有该组");
//        }

//        Set<String> students = userList.getStudents().get(id);
        JSONObject jsonMember = new JSONObject(allGroup.getGroupMember());
        if (jsonMember.isEmpty()) {
            return CommonResult.fail("改组没有成员");
        }
        JSONArray groupMember = (JSONArray)jsonMember.get("name");
        List<String> students = groupMember.toList(String.class);
        if (students == null) {
            return CommonResult.fail("该组没有成员");
        }

//        Set<Integer> week = problemList.getProblems().get(id);
        JSONObject jsonProblem = new JSONObject(allGroup.getGroupProblem());
        if (jsonProblem.isEmpty()) {
            return CommonResult.fail("改组没有问题");
        }
        JSONArray groupProblem = (JSONArray)jsonProblem.get("problem");
        List<Integer> week = groupProblem.toList(Integer.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        HttpHeaders httpHeaders = new HttpHeaders();
//       httpHeaders.set("Cookie", "_ga=GA1.2.2048795898.1663412801; csrftoken=v7W9wLfNgUFB9wgVlsdhHnHSrWZLxBhThEVGi13tzG6g2pUGEp4Pd6e5eeVJ8OW3; sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal; _gid=GA1.2.2056050097.1664683520");
//        httpHeaders.set("Cookie" ,"sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal");




        List<FinalResult> results = new ArrayList<>();
        long t = new Date().getTime();
        for (String student : students) {
//            String url = "https://code.sipcoj.com/api/profile?username=" + student;
            String url = "https://code.sipcoj.com/api/profile?username={student}";

//            System.out.println(url);
//            HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

//            System.out.println(requestEntity);
            Long t1 = new Date().getTime();
            System.out.println( "1:  " + t1);
//            ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
//                    JSONObject.class);
            Map<String, String> map = new HashMap<>();
            map.put("student", student);
            Object forObject = restTemplate.getForObject(url, JSONObject.class, map);
            Long t2 = new Date().getTime();
            System.out.println("2:  " + t2);
            System.out.println( t2 - t1);

//            System.out.println(exchange);
//            System.out.println(exchange.getBody());
            ojResult body = null;
//            if (exchange.toString().contains("User does not exist")) {
//                continue;
//            }
            if (forObject.toString().contains("User does not exist")) {
                continue;
            }

//            try {
//                body = objectMapper.readValue(Objects.requireNonNull(exchange.getBody()).toString(), ojResult.class);
//            }
//            catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
            try {
                body = objectMapper.readValue(Objects.requireNonNull(forObject).toString(), ojResult.class);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            Map<Integer, Problem> problems = body.getData().getAcm_problems_status().getProblems();
            int i = 0;

            if (problems != null) {
                for (Integer pId : week) {
//                    System.out.println(new Date().getTime());
                    Problem problem = problems.get(pId);
                    if (problem != null)
                        ++i;
                }

                results.add(new FinalResult(body.getData().getUser().get("username"), i, week.size()));
            }
            else {
                results.add(new FinalResult(body.getData().getUser().get("username"), 0, week.size()));
            }
        }

        results.sort(((o1, o2) -> o2.getFinish() -o1.getFinish()));

        System.out.println(results);
        System.out.println(new Date().getTime() - t);
        return CommonResult.success(results);
    }
}
