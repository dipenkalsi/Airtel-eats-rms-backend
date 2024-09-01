package com.airteleats.Airtel.eats.service.impl;

import com.airteleats.Airtel.eats.config.JwtProvider;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.repository.UserRepository;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("User not found.");
        }

        return user;
    }
}
