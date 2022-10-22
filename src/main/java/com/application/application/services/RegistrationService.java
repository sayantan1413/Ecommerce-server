package com.application.application.services;

import com.application.application.dto.RegistrationRequest;

public interface RegistrationService {

    public void register(RegistrationRequest request) throws Exception;

    public RegistrationRequest getUserDetail(String email);

}
