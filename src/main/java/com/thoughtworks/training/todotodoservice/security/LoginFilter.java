package com.thoughtworks.training.todotodoservice.security;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.thoughtworks.training.todotodoservice.client.UserClient;
import com.thoughtworks.training.todotodoservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    UserClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = userClient.verifyToken(token);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "", ImmutableList.of()));
        filterChain.doFilter(request, response);


    }
}
