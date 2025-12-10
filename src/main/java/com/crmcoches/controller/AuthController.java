package com.crmcoches.controller;


import com.crmcoches.dto.SignupRequest;
import com.crmcoches.dto.UserDto;
import com.crmcoches.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
       if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
           return new ResponseEntity<>("Ya existe un cliente con este correo electronico", HttpStatus.NOT_ACCEPTABLE);
       UserDto createdCustomerDto = authService.createCustomer(signupRequest);
       if (createdCustomerDto == null) return new ResponseEntity<>
               ("Cliente no creado, intentalo mas tarde", HttpStatus.BAD_REQUEST);

       return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);

    }

}
