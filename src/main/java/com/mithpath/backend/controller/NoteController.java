package com.mithpath.backend.controller;

import com.mithpath.backend.dto.NoteDto;
import com.mithpath.backend.model.Note;
import com.mithpath.backend.response.NoteResponse;
import com.mithpath.backend.service.interfaces.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    @Operation(summary = "Create a new note")
    @PostMapping
    public ResponseEntity<Note> create(@Validated(NoteDto.CreateGroup.class) @RequestBody NoteDto dto) {
        Note saved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Update a note by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(
            @PathVariable Integer id,
            @Validated(NoteDto.UpdateGroup.class) @RequestBody NoteDto dto) {
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
    public ResponseEntity<List<NoteResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Integer tagId,
            @RequestParam(defaultValue = "false") boolean archived) {
        List<NoteResponse> list = service.search(title, content, tagId, archived);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "Delete a note by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Archive a note by ID")
    @PatchMapping("/{id}/archive")
    public ResponseEntity<Note> archiveNote(@PathVariable Integer id) {
        return ResponseEntity.ok(service.archive(id));
    }

    @Operation(summary = "Unarchive a note by ID")
    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<Note> unarchiveNote(@PathVariable Integer id) {
        return ResponseEntity.ok(service.unarchive(id));
    }

}
