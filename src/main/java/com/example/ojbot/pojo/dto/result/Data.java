package com.example.ojbot.pojo.dto.result;

import java.util.Map;

@lombok.Data
public
class Data {
    Integer id;
    String real_name;
    Map<String, String> user;
    Problens acm_problems_status;
    Problens oi_problems_status;

}
