package com.mithpath.backend.controller;

import com.mithpath.backend.dto.TagDto;
import com.mithpath.backend.model.Tag;
import com.mithpath.backend.response.TagResponse;
import com.mithpath.backend.service.interfaces.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;

    @Operation(summary = "Create a new tag")
    @PostMapping
    public ResponseEntity<Tag> create(@Valid @RequestBody TagDto dto) {
        Tag saved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Get a tag by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getById(@PathVariable Integer id) {
        TagResponse found = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @Operation(summary = "Get all tags of the authenticated user")
    @GetMapping("/search")
    public ResponseEntity<List<TagResponse>> search(@RequestParam(required = false) String name) {
        List<TagResponse> list = service.search(name);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "Delete a tag, optionally reassigning notes to another tag")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id,
                                       @RequestParam(required = false) Integer newTagId) {
        service.delete(id, newTagId);
        return ResponseEntity.noContent().build();
    }
}
