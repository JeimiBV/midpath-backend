package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.dto.AuthDto;
import com.mithpath.backend.model.User;

import java.util.Map;

public interface AuthService {
    User register(AuthDto dto);
    Map<String, String> login(AuthDto dto);
    User getProfile();
}
