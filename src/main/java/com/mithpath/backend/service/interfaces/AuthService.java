package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.dto.AuthDto;
import com.mithpath.backend.model.User;

public interface AuthService {
    User register(AuthDto dto);
    String login(AuthDto dto);
    User getProfile();
}
