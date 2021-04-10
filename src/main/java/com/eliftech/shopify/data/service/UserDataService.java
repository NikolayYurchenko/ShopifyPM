package com.eliftech.shopify.data.service;

import com.eliftech.shopify.data.entity.User;
import com.eliftech.shopify.data.repository.UserRepository;
import com.eliftech.shopify.model.UserForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDataService {

    private final UserRepository userRepository;

    /**
     * Create user
     * @param request
     */
    public void create(UserForm request) {

        log.info("Creating user from data:[{}]", request);

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .emailStatus(request.getEmailStatus())
                .loginSessionKey(request.getLoginSessionKey())
                .passwordExpireData(request.getPasswordExpireData())
                .passwordResetKey(request.getPasswordResetKey())
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }

    /**
     * Find all users
     * @return
     */
    public List<User> findAll() {

        log.info("Searching all users");

        return userRepository.findAll();
    }
}
