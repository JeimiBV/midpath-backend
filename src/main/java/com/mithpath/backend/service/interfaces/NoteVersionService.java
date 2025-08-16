package com.mithpath.backend.service.interfaces;

import com.mithpath.backend.model.NoteVersion;
import com.mithpath.backend.response.NoteVersionResponse;

import java.util.List;

public interface NoteVersionService {
    List<NoteVersionResponse> getVersions(Integer noteId);
    NoteVersion revertToVersion(Integer noteId, Integer versionId);
}
