package com.mithpath.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteDto {
    @NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "{note.title.not-null}")
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class}, message = "{note.title.not-blank}")
    private String title;

    @NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "{note.content.not-null}")
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class}, message = "{note.content.not-blank}")
    private String content;

    @NotNull(groups = {CreateGroup.class}, message = "{note.tagId.not-null}")
    private Integer tagId;


    public interface CreateGroup {}
    public interface UpdateGroup {}
}
