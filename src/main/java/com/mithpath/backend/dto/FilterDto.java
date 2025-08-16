package com.mithpath.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterDto {
    private String title;
    private List<Integer> tagIds;
    private boolean archived;
}
