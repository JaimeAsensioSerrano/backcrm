package com.crmcoches.services.auth;

import com.crmcoches.dto.SignupRequest;
import com.crmcoches.dto.UserDto;
import org.aspectj.weaver.SignatureUtils;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);
}
