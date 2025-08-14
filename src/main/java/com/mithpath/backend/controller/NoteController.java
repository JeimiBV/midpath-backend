package com.mithpath.backend.controller;

import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.response.NoteResponse;
import com.mithpath.backend.service.interfaces.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    @Operation(summary = "Create a new note")
    @PostMapping
    public ResponseEntity<Note> create(@RequestBody NoteDto dto) {
        Note saved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Update a note by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Integer id, @RequestBody NoteDto dto) {
        Note found = service.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @Operation(summary = "Get a note by ID")
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getById(@PathVariable Integer id) {
        NoteResponse found = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @Operation(summary = "Get all notes")
    @GetMapping("/search")
    public ResponseEntity<List<NoteResponse>> search(@RequestParam(required = false) String title) {
        List<NoteResponse> list = service.search(title);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
