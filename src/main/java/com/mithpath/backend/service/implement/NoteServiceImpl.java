package com.mithpath.backend.service.implement;

import com.mithpath.backend.common.enums.RoleName;
import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.exception.EntityNotFoundException;
import com.mithpath.backend.exception.MessageUtil;
import com.mithpath.backend.filter.NoteFilter;
import com.mithpath.backend.mapper.NoteMapper;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.model.NoteVersion;
import com.mithpath.backend.model.Tag;
import com.mithpath.backend.model.User;
import com.mithpath.backend.repository.NoteRepository;
import com.mithpath.backend.repository.NoteVersionRepository;
import com.mithpath.backend.repository.TagRepository;
import com.mithpath.backend.repository.UserRepository;
import com.mithpath.backend.response.NoteResponse;
import com.mithpath.backend.service.interfaces.NoteService;
import com.mithpath.backend.service.interfaces.UserSearchStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository repository;
    private final NoteMapper mapper;

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserSearchStateService searchStateService;
    private final NoteVersionRepository noteVersionRepository;

    private static final String ENTITY_NAME = Note.class.getSimpleName();

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageUtil.getProperty("exception.authentication.userNotFound.message")));
    }

    @Override
    public Note create(NoteDto dto) {
        User user = getCurrentUser();
        Tag tag = tagRepository.findByIdAndUserAndActiveTrue(dto.getTagId(), user)
                .orElseThrow(() -> new EntityNotFoundException("Tag", dto.getTagId()));

        Note note = mapper.fromDto(dto);
        note.setUser(user);
        note.setTag(tag);

        return repository.save(note);
    }

    @Override
    public Note update(Integer id, NoteDto dto) {
        User user = getCurrentUser();

        Note found = repository.findNoteByIdAndUserAndActiveTrue(id, user)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));

        saveVersion(found);

        Note updated = mapper.fromDto(dto, found);
        return repository.save(updated);
    }

    @Override
    public List<NoteResponse> search(NoteFilter filter) {
        User user = getCurrentUser();

        if (filter == null) {
            filter = searchStateService.getFilters("note", NoteFilter.class);
        } else {
            searchStateService.saveFilters("note", filter, user);
        }

        if (user.getRole() == RoleName.ADMIN) {
            return repository.searchAll(filter);
        } else {
            return repository.searchByUser(user, filter);
        }
    }


    @Override
    public NoteResponse findById(Integer id) {
        User user = getCurrentUser();
        return repository.findByUserAndId(user, id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
    }

    @Override
    public void delete(Integer id) {
        User user = getCurrentUser();

        Note found = repository.findNoteByIdAndUserAndActiveTrue(id, user)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
        saveVersion(found);
        found.setActive(false);
        repository.save(found);
    }

    @Override
    public Note archive(Integer id) {
        User user = getCurrentUser();

        Note found = repository.findNoteByIdAndUserAndActiveTrue(id, user)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
        saveVersion(found);
        found.setArchived(true);
        return repository.save(found);
    }

    @Override
    public Note unarchive(Integer id) {
        User user = getCurrentUser();

        Note found = repository.findNoteByIdAndUserAndActiveTrue(id, user)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
        saveVersion(found);
        found.setArchived(false);
        return repository.save(found);
    }

    private void saveVersion(Note note) {
        NoteVersion version = NoteVersion.builder()
                .note(note)
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.getArchived())
                .build();

        noteVersionRepository.save(version);
    }
}
