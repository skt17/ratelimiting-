package com.redis.ratelimiting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class Controller {

    @Autowired
    RedisTemplate<String, Integer> redisTemplate;
    private final Integer max_api_limit = 2;
    @GetMapping("/ping/{id}")
    public ResponseEntity test(@PathVariable String id) {
        ValueOperations<String, Integer> valueOps = redisTemplate.opsForValue();
        Integer count = valueOps.get(id);

        if (count.equals(0)) {
            log.info("User with id {} has reached the threshold ", id);
            return new ResponseEntity<>(HttpStatusCode.valueOf(429));
        } else if (count == null) {
            log.info("no value found for {}. Setting the value to [{}] with TTL of [{}] minutes", id, count);
            valueOps.set(id, max_api_limit, 1, TimeUnit.MINUTES);
        } else {
            valueOps.decrement(id);
            log.info("Updated the count as {} for  id {}", count - 1, id);

        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/hello/")
    public void test() {
        log.info("Test");
    }
}
