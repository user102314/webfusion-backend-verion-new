package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/simple")
    public Map<String, Object> simpleTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Backend is working!");
        return response;
    }

    @GetMapping("/date-test")
    public Map<String, Object> dateTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("localDateTime", LocalDateTime.now());
        response.put("date", new java.util.Date());
        return response;
    }
}