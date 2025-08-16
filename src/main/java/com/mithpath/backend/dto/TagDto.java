package com.mithpath.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDto {
    @NotNull(message = "{tag.name.not-null}")
    @NotBlank(message = "{tag.name.not-blank}")
    private String name;
}
