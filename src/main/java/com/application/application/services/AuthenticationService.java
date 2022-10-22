package com.application.application.services;

import com.application.application.model.User;

public interface AuthenticationService {

    public User authenticateUser(String email);

}
