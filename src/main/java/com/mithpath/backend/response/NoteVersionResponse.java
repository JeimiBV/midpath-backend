package com.mithpath.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteVersionResponse {
    private Integer id;
    private String title;
    private String content;
    private Boolean archived;
    private Integer noteId;
}
