package com.customer.service;

import com.customer.bean.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    public UserProfile getUserProfile(Long id) {

        return UserProfile.builder()
                .name("John")
                .email("john.kick@gmail.com")
                .build();
    }
}
