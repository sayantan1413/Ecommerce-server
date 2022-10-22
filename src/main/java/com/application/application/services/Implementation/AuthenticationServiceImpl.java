package com.application.application.services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.application.model.User;
import com.application.application.repository.UserDao;
import com.application.application.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Override
    public User authenticateUser(String email) {
        return userDao.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Illegal access to account"));
    }

}
