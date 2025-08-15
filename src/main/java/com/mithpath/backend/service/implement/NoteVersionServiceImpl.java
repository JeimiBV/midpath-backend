package com.mithpath.backend.service.implement;

import com.mithpath.backend.exception.EntityNotFoundException;
import com.mithpath.backend.exception.MessageUtil;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.model.NoteVersion;
import com.mithpath.backend.model.User;
import com.mithpath.backend.repository.NoteRepository;
import com.mithpath.backend.repository.NoteVersionRepository;
import com.mithpath.backend.repository.UserRepository;
import com.mithpath.backend.response.NoteVersionResponse;
import com.mithpath.backend.service.interfaces.NoteVersionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteVersionServiceImpl implements NoteVersionService {
    private final UserRepository userRepository;
    private final NoteVersionRepository versionRepository;
    private final NoteRepository noteRepository;

    private final static String ENTITY_NAME = NoteVersion.class.getSimpleName();
    private final static String NOTE_ENTITY_NAME = Note.class.getSimpleName();


    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageUtil.getProperty("exception.authentication.userNotFound.message")));
    }

    @Override
    public List<NoteVersionResponse> getVersions(Integer noteId) {
        return versionRepository.findAllVersionsByNoteId(noteId);
    }

    @Override
    public NoteVersion revertToVersion(Integer noteId, Integer versionId) {
        User user = getCurrentUser();
        Note note = noteRepository.findNoteByIdAndUserAndActiveTrue(noteId, user)
                .orElseThrow(() -> new EntityNotFoundException(NOTE_ENTITY_NAME, noteId));

        NoteVersion version = versionRepository.findById(versionId)
                .filter(v -> v.getNote().getId().equals(noteId))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, versionId));

        note.setTitle(version.getTitle());
        note.setContent(version.getContent());
        noteRepository.save(note);

        NoteVersion newVersion = NoteVersion.builder()
                .note(note)
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.getArchived())
                .build();
        return versionRepository.save(newVersion);
    }
}
