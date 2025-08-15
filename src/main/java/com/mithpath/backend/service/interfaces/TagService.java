package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.dto.TagDto;
import com.mithpath.backend.model.Tag;
import com.mithpath.backend.response.TagResponse;

import java.util.List;

public interface TagService {
    Tag create(TagDto dto);
    TagResponse findById(Integer id);
    List<TagResponse> search(String name);
    void delete(Integer id, Integer newTagId);
}
