package com.mithpath.backend.service.implement;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mithpath.backend.exception.EntityNotFoundException;
import com.mithpath.backend.exception.MessageUtil;
import com.mithpath.backend.model.User;
import com.mithpath.backend.model.UserSearchState;
import com.mithpath.backend.repository.UserRepository;
import com.mithpath.backend.repository.UserSearchStateRepository;
import com.mithpath.backend.service.interfaces.UserSearchStateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchStateServiceImpl implements UserSearchStateService {

    private final UserSearchStateRepository repository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageUtil.getProperty("exception.authentication.userNotFound.message")));
    }

    @Override
    public <T> T getFilters(String type, Class<T> clazz) {
        User user = getCurrentUser();
        return repository.findByUserAndEntity(user, type)
                .map(UserSearchState::getFiltersJson)
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, clazz);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Invalid filter JSON");
                    }
                })
                .orElseGet(() -> {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot create filter instance");
                    }
                });
    }
    @Transactional
    @Override
    public <T> void saveFilters(String type, T filters, User user) {
        try {
            String json = objectMapper.writeValueAsString(filters);
            UserSearchState state = repository.findByUserAndEntity(user, type)
                    .orElse(new UserSearchState());
            state.setUser(user);
            state.setEntity(type);
            state.setFiltersJson(json);
            repository.save(state);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot serialize filters");
        }
    }
}
