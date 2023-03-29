package com.redis.ratelimiting;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RatelimitingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatelimitingApplication.class, args);
	}


}
