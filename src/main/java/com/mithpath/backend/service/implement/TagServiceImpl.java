package com.mithpath.backend.service.implement;

import com.mithpath.backend.dto.TagDto;
import com.mithpath.backend.exception.EntityNotFoundException;
import com.mithpath.backend.exception.MessageUtil;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.model.Tag;
import com.mithpath.backend.model.User;
import com.mithpath.backend.repository.NoteRepository;
import com.mithpath.backend.repository.TagRepository;
import com.mithpath.backend.repository.UserRepository;
import com.mithpath.backend.response.TagResponse;
import com.mithpath.backend.service.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    private static final String ENTITY_NAME = Tag.class.getSimpleName();

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageUtil.getProperty("exception.authentication.userNotFound.message")));
    }

    @Override
    public Tag create(TagDto dto) {
        User user = getCurrentUser();

        Tag tag = Tag.builder()
                .name(dto.getName())
                .user(user)
                .build();
        return repository.save(tag);
    }

    @Override
    public TagResponse findById(Integer id) {
        User user = getCurrentUser();
        return repository.findTagByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
    }

    @Override
    public List<TagResponse> search(String name) {
        User user = getCurrentUser();
        return repository.search(user);
    }

    @Override
    public void delete(Integer id, Integer newTagId) {
        User user = getCurrentUser();

        Tag tagToDelete = repository.findByIdAndUserAndActiveTrue(id, user)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));

        List<Note> notesToReassign = noteRepository.findAllByTagAndActiveTrue(tagToDelete);

        if (newTagId != null) {
            Tag reassignTag = repository.findByIdAndUserAndActiveTrue(newTagId, user)
                    .orElseThrow(() -> new EntityNotFoundException("Reassign Tag", newTagId));

            notesToReassign.forEach(note -> note.setTag(reassignTag));
        } else {
            notesToReassign.forEach(note -> note.setTag(null));
        }
        noteRepository.saveAll(notesToReassign);
        repository.delete(tagToDelete);
    }
}
