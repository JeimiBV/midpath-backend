package com.mithpath.backend.controller;

import com.mithpath.backend.model.NoteVersion;
import com.mithpath.backend.response.NoteVersionResponse;
import com.mithpath.backend.service.interfaces.NoteVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes-versions")
@RequiredArgsConstructor
public class NoteVersionController {
    private final NoteVersionService versionService;

    @GetMapping("/{noteId}")
    public ResponseEntity<List<NoteVersionResponse>> getVersions(@PathVariable Integer noteId) {
        List<NoteVersionResponse> versions = versionService.getVersions(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(versions);
    }

    @PostMapping("/revert")
    public ResponseEntity<NoteVersion> revertVersion(
            @RequestParam Integer noteId,
            @RequestParam Integer versionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(versionService.revertToVersion(noteId, versionId));
    }
}
