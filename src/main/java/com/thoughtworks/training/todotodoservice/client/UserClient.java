package com.thoughtworks.training.todotodoservice.client;

import com.thoughtworks.training.todotodoservice.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user",url = "localhost:8082")
public interface UserClient {
    @PostMapping(value = "/users/verification")
    User verifyToken(String token);
}
