package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.filter.NoteFilter;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.response.NoteResponse;

import java.util.List;

public interface NoteService {
    Note create(NoteDto dto);
    Note update(Integer id, NoteDto dto);
    List<NoteResponse> search(NoteFilter filter);
    NoteResponse findById(Integer id);
    void delete(Integer id);
    Note archive(Integer id);
    Note unarchive(Integer id);
}
