package com.crmcoches.controller;


import com.crmcoches.dto.AuthenticationRequest;
import com.crmcoches.dto.AuthenticationResponse;
import com.crmcoches.dto.SignupRequest;
import com.crmcoches.dto.UserDto;
import com.crmcoches.entity.User;
import com.crmcoches.repository.UserRepository;
import com.crmcoches.services.auth.AuthService;
import com.crmcoches.services.auth.jwt.UserService;
import com.crmcoches.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException; // <--- Necesario
import org.springframework.security.authentication.DisabledException;       // <--- Necesario
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // <--- Necesario


import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JWTUtil jwtUtil;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
       if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
           return new ResponseEntity<>("Ya existe un cliente con este correo electronico", HttpStatus.NOT_ACCEPTABLE);
       UserDto createdCustomerDto = authService.createCustomer(signupRequest);
       if (createdCustomerDto == null) return new ResponseEntity<>
               ("Cliente no creado, intentalo mas tarde", HttpStatus.BAD_REQUEST);

       return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("usuario o contraseña incorrecto");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
    }



}

