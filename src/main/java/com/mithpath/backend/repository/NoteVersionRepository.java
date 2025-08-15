package com.mithpath.backend.repository;

import com.mithpath.backend.model.Note;
import com.mithpath.backend.model.NoteVersion;
import com.mithpath.backend.response.NoteVersionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteVersionRepository extends JpaRepository<NoteVersion, Integer> {
    @Query("""
        SELECT new com.mithpath.backend.response.NoteVersionResponse(
           nv.id, nv.title, nv.content, nv.note.archived, nv.note.id)
        FROM NoteVersion nv
        WHERE nv.note.id = :noteId
        ORDER BY nv.updatedAt DESC
    """)
    List<NoteVersionResponse> findAllVersionsByNoteId(Integer noteId);
}

