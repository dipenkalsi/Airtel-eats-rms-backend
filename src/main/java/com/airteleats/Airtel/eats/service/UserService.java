package com.airteleats.Airtel.eats.service;

import com.airteleats.Airtel.eats.model.User;
import jdk.jshell.spi.ExecutionControl;

import java.util.concurrent.ExecutionException;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;


}
