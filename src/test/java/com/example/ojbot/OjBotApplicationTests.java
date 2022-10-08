package com.example.ojbot;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.ojbot.mapper.AllGroupMapper;
import com.example.ojbot.pojo.dto.AllGroup;
import com.example.ojbot.utils.TaskTimeWork;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OjBotApplicationTests {

//    private RestTemplateConfig restTemplateConfig = new RestTemplateConfig();

    @Resource
    private TaskTimeWork taskTimeWork;

    @Autowired
    private RestTemplate restTemplate;

//    @Resource
//    private AllGroupMapper allGroupMapper;

    @Test
    void contextLoads() {
//        RestTemplate restTemplate = restTemplateConfig.restTemplate();

        String url = "https://code.sipcoj.com/api/profile?username=22-何智博";
//        Object forObject = restTemplate.getForObject(url, String.class);
//        System.out.println(forObject.toString());

        HttpHeaders httpHeaders = new HttpHeaders();
//       httpHeaders.set("Cookie", "_ga=GA1.2.2048795898.1663412801; csrftoken=v7W9wLfNgUFB9wgVlsdhHnHSrWZLxBhThEVGi13tzG6g2pUGEp4Pd6e5eeVJ8OW3; sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal; _gid=GA1.2.2056050097.1664683520");
        httpHeaders.set("Cookie" ,"sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal");
        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

//        System.out.println(requestEntity);
//        URL url1;
//        try {
//             url1 = new URL(url);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        for (int i = 0; i < 5; ++i) {
//            long t1 = new Date().getTime();
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);

        JSONObject body = exchange.getBody();

        assert body != null;
        JSONObject jsonObject = new JSONObject(body.get("data"));
        Object problems = jsonObject.get("acm_problems_status");

        JSONObject jsonObject1 = new JSONObject(problems);

        List<String> name = new ArrayList<>();
//        name.add("")
//        jsonObject1.toJSONArray()

        System.out.println(jsonObject1);

//            try {
////                ParameterizedTypeReference
//
////                StringUtil
//
//                URLConnection urlConnection = url1.openConnection();
//                urlConnection.connect();
//                InputStream inputStream = urlConnection.getInputStream();
//
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            long t2 = new Date().getTime();
//            System.out.println(t2-t1);
//        }

//        System.out.println(exchange);
//        System.out.println(exchange.getBody());




//        List<AllGroup> allGroups = allGroupMapper.selectList(null);
////        System.out.println(allGroups);
//        Object groupMember = allGroups.get(0).getGroupMember();
//        JSONObject jsonObject = new JSONObject(groupMember);
//        System.out.println(jsonObject);
////        List<String> list = new ArrayList<>();
////        list.add("name");
////        JSONArray objects = jsonObject.toJSONArray(list);
////        System.out.println(Arrays.toString(objects.toArray()));
//        JSONArray name = (JSONArray)jsonObject.get("name");
//        List<String> list = name.toList(String.class);
//        System.out.println(name);
//        System.out.println(list.get(0));


//        taskTimeWork.schedule();

    }

}
