package com.crmcoches.services.auth;


import com.crmcoches.dto.SignupRequest;
import com.crmcoches.dto.UserDto;
import com.crmcoches.entity.User;
import com.crmcoches.enums.UserRole;
import com.crmcoches.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository; // inyeccion del repositorio usuarios

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {

        User user= new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
