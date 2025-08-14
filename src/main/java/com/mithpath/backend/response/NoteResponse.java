package com.mithpath.backend.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {
    private Integer id;
    private String title;
    private String content;
}
