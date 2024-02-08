package com.customer.bean;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfile {

    private String name;
    private String email;
    private String phoneNumber;

}