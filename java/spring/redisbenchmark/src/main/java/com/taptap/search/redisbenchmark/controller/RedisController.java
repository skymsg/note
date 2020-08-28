package com.taptap.search.redisbenchmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping(value="/get",produces = "text/plain")
    public String set(@RequestParam(value = "key") String key){
        var a = redisTemplate.opsForValue().get(key);
        return a;
    }


}
