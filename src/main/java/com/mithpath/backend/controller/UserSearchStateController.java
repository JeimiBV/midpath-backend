package com.mithpath.backend.controller;

import com.mithpath.backend.filter.NoteFilter;
import com.mithpath.backend.service.interfaces.UserSearchStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search-state")
@RequiredArgsConstructor
public class UserSearchStateController {

    private final UserSearchStateService service;

    @GetMapping()
    public ResponseEntity<NoteFilter> getSearchState(@RequestParam() String type) {
        NoteFilter filters = service.getFilters(type, NoteFilter.class);
        return ResponseEntity.ok(filters);
    }
}
