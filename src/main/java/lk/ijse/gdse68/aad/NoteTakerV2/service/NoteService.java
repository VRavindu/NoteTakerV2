package lk.ijse.gdse68.aad.NoteTakerV2.service;


import lk.ijse.gdse68.aad.NoteTakerV2.customObj.NoteResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    String saveNote(NoteDTO noteDTO);
    void updateNote(String noteId, NoteDTO noteDTO);
    void deleteNote(String noteId);
    NoteResponse getSelectedNote(String noteId);
    List<NoteDTO> getAllNotes();
}
