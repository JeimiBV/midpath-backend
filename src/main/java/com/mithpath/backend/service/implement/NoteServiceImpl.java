package com.mithpath.backend.service.implement;

import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.mapper.NoteMapper;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.model.User;
import com.mithpath.backend.repository.NoteRepository;
import com.mithpath.backend.repository.UserRepository;
import com.mithpath.backend.response.NoteResponse;
import com.mithpath.backend.service.interfaces.NoteService;
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

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Note create(NoteDto dto) {
        User user = getCurrentUser();

        Note note = mapper.fromDto(dto);
        note.setUser(user);

        return repository.save(note);
    }

    @Override
    public Note update(Integer id, NoteDto dto) {
        User user = getCurrentUser();
        Note found = repository.findNoteByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        Note updated = mapper.fromDto(dto, found);
        return repository.save(updated);
    }

    @Override
    public List<NoteResponse> search(String title) {
        User user = getCurrentUser();
        return repository.search(user, title);
    }

    @Override
    public NoteResponse findById(Integer id) {
        User user = getCurrentUser();
        return repository.findByUserAndId(user, id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public void delete(Integer id) {
        User user = getCurrentUser();
        Note found = repository.findNoteByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        found.setActive(false);
        repository.save(found);
    }
}
