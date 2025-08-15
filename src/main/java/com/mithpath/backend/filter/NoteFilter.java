package com.mithpath.backend.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteFilter {
    private String title;
    private String content;
    private Integer tagId;
    private Boolean archived;
}
