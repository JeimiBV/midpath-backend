package com.mithpath.backend.repository;

import com.mithpath.backend.model.User;
import com.mithpath.backend.model.UserSearchState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSearchStateRepository extends JpaRepository<UserSearchState, Integer> {
    Optional<UserSearchState> findByUserAndEntity(User user, String entity);
}
