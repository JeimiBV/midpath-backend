package com.mithpath.backend.repository;

import com.mithpath.backend.model.Tag;
import com.mithpath.backend.model.User;
import com.mithpath.backend.response.TagResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("""
        SELECT new com.mithpath.backend.response.TagResponse(t.id, t.name)
        FROM Tag t
        WHERE t.user = :user AND t.id = :id
        AND t.active
    """)
    Optional<TagResponse> findTagByIdAndUser(@Param("id") Integer id, @Param("user") User user);

    @Query("""
        SELECT new com.mithpath.backend.response.TagResponse(t.id, t.name)
        FROM Tag t
        WHERE t.user = :user
        AND t.active
    """)
    List<TagResponse> search(@Param("user") User user);

    Optional<Tag> findByIdAndUserAndActiveTrue(Integer id, User user);
}
