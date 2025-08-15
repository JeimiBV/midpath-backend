package com.mithpath.backend.repository;

import com.mithpath.backend.filter.NoteFilter;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.model.Tag;
import com.mithpath.backend.model.User;
import com.mithpath.backend.response.NoteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("""
        SELECT new com.mithpath.backend.response.NoteResponse(
            n.id, n.title, n.content, n.tag.id, n.tag.name
        )
        FROM Note n
        WHERE n.user = :user
          AND (:#{#filter.title} IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :#{#filter.title}, '%')))
          AND (:#{#filter.content} IS NULL OR LOWER(n.content) LIKE LOWER(CONCAT('%', :#{#filter.content}, '%')))
          AND (:#{#filter.tagId} IS NULL OR n.tag.id IN :#{#filter.tagId})
          AND (:#{#filter.archived} IS NULL OR n.archived = :#{#filter.archived})
          AND n.active = true
    """)
    List<NoteResponse> search(
            @Param("user") User user,
            @Param("filter") NoteFilter filter
    );


    @Query("""
        SELECT new com.mithpath.backend.response.NoteResponse(n.id, n.title, n.content, n.tag.id, n.tag.name)
        FROM Note n
        WHERE n.user = :user AND n.id = :id
        AND n.active
    """)
    Optional<NoteResponse> findByUserAndId(
            @Param("user") User user,
            @Param("id") Integer id);

    Optional<Note> findNoteByIdAndUserAndActiveTrue(Integer id, User user);
    List<Note> findAllByTagAndActiveTrue(Tag tag);


}
