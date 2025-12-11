package com.crmcoches.dto;

import com.crmcoches.enums.UserRole;
import lombok.Data;

@Data

public class AuthenticationResponse {

    private String jwt;

    private UserRole userRole;

    private Long userId;
}
