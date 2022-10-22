package com.application.application.services.Implementation;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.application.dto.RegistrationRequest;
import com.application.application.model.User;
import com.application.application.model.UserRole;
import com.application.application.repository.UserDao;
import com.application.application.services.RegistrationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationServiceImp implements RegistrationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder encoder;

    private static boolean validateEmail(String emailAddress) {
        final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @Override
    public void register(RegistrationRequest request) {
        if (!validateEmail(request.getEmail())) {
            throw new IllegalStateException("Email not valid");
        }
        System.out.println("hello");
        User user = new User(
                request.getCompanyName(),
                request.getPassword(),
                request.getEmail(),
                request.getPhoneNo(),
                request.getGstNo(),
                request.getAddress(),
                request.getManufacturer() ? UserRole.ROLE_SELLER : UserRole.ROLE_USER);
        boolean userExists = userDao.findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userDao.save(user);
    }

    @Override
    public RegistrationRequest getUserDetail(String email) {
        User user = userDao.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not valid!!!!"));

        return new RegistrationRequest(user.getCompanyName(), email, null, user.getPhoneNo(), user.getGstNo(),
                user.getAddress(), null);
    }

}
