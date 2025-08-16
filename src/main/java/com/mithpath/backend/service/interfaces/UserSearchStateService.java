package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.model.User;

public interface UserSearchStateService {
    <T> T getFilters(String type, Class<T> clazz);
    <T> void saveFilters(String type, T filters, User user);
}
