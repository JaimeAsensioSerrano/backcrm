package com.crmcoches.dto;

import com.crmcoches.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String name;

    private String email;

    private UserRole userRole;
}
