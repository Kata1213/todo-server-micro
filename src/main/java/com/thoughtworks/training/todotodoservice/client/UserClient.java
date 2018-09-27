package com.thoughtworks.training.todotodoservice.client;

import com.thoughtworks.training.todotodoservice.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "user",url = "localhost:8082")
@FeignClient(name = "user-servive")

public interface UserClient {
    @PostMapping(value = "/users/verifications")
    User verifyToken(@RequestHeader("Authorization") String token);
}
