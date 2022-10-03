package com.example.ojbot.pojo.dto.result;

import java.util.Map;

@lombok.Data
public class Problens {
    Map<Integer, Problem> problems;
    Map<Integer, Problem> contest_problems;
}
