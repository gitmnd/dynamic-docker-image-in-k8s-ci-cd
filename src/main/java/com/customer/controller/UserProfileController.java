package com.customer.controller;

import com.customer.bean.UserProfile;
import com.customer.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/user/{id}")
    UserProfile getUserProfile(@PathVariable Long id) {
        log.info("Get user for user id {}", id);
        return userProfileService.getUserProfile(id);
    }

    @GetMapping("/ping")
    String getPing() {
        return "API Service is ALIVE..";
    }

}
