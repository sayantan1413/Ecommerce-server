package com.application.application.services.Implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.application.model.AppUserDetails;
import com.application.application.model.User;
import com.application.application.repository.UserDao;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserDao userDao;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String email)
                        throws UsernameNotFoundException {
                User user = userDao.findByEmail(email)
                                .orElseThrow(
                                                () -> new UsernameNotFoundException(
                                                                "You don't have a account.. Please register"));

                return AppUserDetails.build(user);
        }

}
