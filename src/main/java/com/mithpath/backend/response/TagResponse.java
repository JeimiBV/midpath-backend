package com.mithpath.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagResponse {
    private Integer id;
    private String name;
}
