package lk.ijse.gdse68.aad.NoteTakerV2.service;

import jakarta.transaction.Transactional;
import lk.ijse.gdse68.aad.NoteTakerV2.customObj.NoteErrorResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.customObj.NoteResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.NoteDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.entity.NoteEntity;
import lk.ijse.gdse68.aad.NoteTakerV2.exception.NoteNotFoundExeption;
import lk.ijse.gdse68.aad.NoteTakerV2.repository.NoteRepository;
import lk.ijse.gdse68.aad.NoteTakerV2.util.AppUtil;
import lk.ijse.gdse68.aad.NoteTakerV2.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final Mapping mapping;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, Mapping mapping) {
        this.noteRepository = noteRepository;
        this.mapping = mapping;
    }

    @Override
    public String saveNote(NoteDTO noteDTO) {
        noteDTO.setId(AppUtil.createNoteId());
        NoteEntity savedNote = noteRepository.save(mapping.convertToEntity(noteDTO));
        if (savedNote != null && savedNote.getId() != null) {
            return "Note saved successfully";
        }else {
            return "Save Failed";
        }
    }

    @Override
    public void updateNote(String id, NoteDTO incomingNoteDTO) {
        Optional<NoteEntity> tmpNoteEntity = noteRepository.findById(id);
        if (!tmpNoteEntity.isPresent()){
            throw new NoteNotFoundExeption("Note Not Found");
        }else {
            tmpNoteEntity.get().setNoteTitle(incomingNoteDTO.getNoteTitle());
            tmpNoteEntity.get().setNoteDesc(incomingNoteDTO.getNoteDesc());
            tmpNoteEntity.get().setPriorityLevel(incomingNoteDTO.getPriorityLevel());
            tmpNoteEntity.get().setCreateDate(incomingNoteDTO.getCreateDate());
        }
    }

    @Override
    public void deleteNote(String id) {
        Optional<NoteEntity> findId = noteRepository.findById(id);
        if (!findId.isPresent()){
            throw new NoteNotFoundExeption("Note Not Found");
        }else {
            noteRepository.deleteById(id);
        }
    }

    @Override
    public NoteResponse getSelectedNote(String id) {
        if (noteRepository.existsById(id)) {
            return mapping.convertToDTO(noteRepository.getReferenceById(id));
        }else {
            return new NoteErrorResponse( 0 ,"Note Not Found");
        }
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return mapping.convertToDTO(noteRepository.findAll());
    }
}
