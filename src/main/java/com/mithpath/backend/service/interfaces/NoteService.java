package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.response.NoteResponse;

import java.util.List;

public interface NoteService {
    Note create(NoteDto dto);
    Note update(Integer id, NoteDto dto);
    List<NoteResponse> search(String title);
    NoteResponse findById(Integer id);
    void delete(Integer id);
}
