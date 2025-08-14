package com.mithpath.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteDto {
    @NotNull(message = "{note.title.not-null}")
    @NotBlank(message = "{note.title.not-blank}")
    private String title;

    @NotNull(message = "{note.content.not-null}")
    @NotBlank(message = "{note.content.not-blank}")
    private String content;
}
