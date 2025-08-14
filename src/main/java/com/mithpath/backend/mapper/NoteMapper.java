package com.mithpath.backend.mapper;

import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public Note fromDto(NoteDto dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return note;
    }

    public Note fromDto(NoteDto dto, Note found) {
        found.setTitle(dto.getTitle());
        found.setContent(dto.getContent());
        return found;
    }
}
