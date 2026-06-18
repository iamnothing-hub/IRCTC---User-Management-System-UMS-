package com.irctc.userservice.service.impl;

import com.irctc.userservice.dto.request.CreateUserRequest;
import com.irctc.userservice.dto.request.ResetPasswordRequest;
import com.irctc.userservice.dto.response.CreateUserResponse;
import com.irctc.userservice.entity.PasswordResetToken;
import com.irctc.userservice.entity.Role;
import com.irctc.userservice.entity.User;
import com.irctc.userservice.enums.AccountStatus;
import com.irctc.userservice.exception.BadRequestException;
import com.irctc.userservice.exception.DuplicateException;
import com.irctc.userservice.exception.ResourceNotFoundException;
import com.irctc.userservice.repository.RoleRepository;
import com.irctc.userservice.repository.UserRepository;
import com.irctc.userservice.service.*;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UsernameGeneratorService usernameGeneratorService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;


    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {

        User user1 = userRepository.findByEmail(createUserRequest.getEmail());
        if(user1 != null) {
            throw new DuplicateException("Email is already exist.");
        }

        String username = usernameGeneratorService.generateUsername(createUserRequest.getFirstName(), createUserRequest.getLastName());

        String tempPassword = passwordGeneratorService.generatePassword();

        User user = User.builder()
                .password(passwordEncoder.encode(tempPassword))
                .roles(fetchRole(createUserRequest.getRoles()))
                .username(username)
                .email(createUserRequest.getEmail())
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .passwordResetRequired(true)
                .accountStatus(AccountStatus.PENDING_PASSWORD_RESET)
                .build();

        User savedUser = userRepository.save(user);
        PasswordResetToken token = passwordResetTokenService.createToken(savedUser);
        emailService.setPasswordSetupEmail(savedUser, token.getToken());
        /**
         * In Future we will use Kafka or RabitMQ, Publish Event, Notification Service etc for when email send failure prevention
         *
         * */


        return CreateUserResponse.builder()
                .username(savedUser.getUsername())
                .userId(savedUser.getId())
                .message("User created successfully")
                .build();
    }

    @Override
    public void setPassword(ResetPasswordRequest request) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.validateToken(request.getToken());
        if(passwordResetToken == null) throw new BadRequestException("Token is not matched.");

        User user = userRepository.findById(passwordResetToken.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setPasswordResetRequired(false);
        userRepository.save(user);

        passwordResetTokenService.markAsUsed(passwordResetToken);
    }

    private Set<Role> fetchRole(@NotBlank(message = "Role should not be empty.") Set<String> rolesname) {

        Set<Role> roles = rolesname.stream().map(rolename -> roleRepository.findByRoleName(rolename).orElseThrow(() -> new ResourceNotFoundException("Role not found: " + rolename))).collect(Collectors.toSet());
        return roles;
    }
}
