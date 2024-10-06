package lk.ijse.gdse68.aad.NoteTakerV2.controller;

import lk.ijse.gdse68.aad.NoteTakerV2.customObj.NoteResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.NoteDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.exception.NoteNotFoundExeption;
import lk.ijse.gdse68.aad.NoteTakerV2.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/note")
@RequiredArgsConstructor
public class NoteController {
    @Autowired
    private final NoteService noteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNote(@RequestBody NoteDTO noteDTO) {
        var isSaved = noteService.saveNote(noteDTO);
        if (isSaved.contains("Note saved successfully")){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value ="allNotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteResponse getNote(@PathVariable("noteId") String noteId) {
        return noteService.getSelectedNote(noteId);
    }

    @PatchMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateNote(@PathVariable ("noteId") String noteId, @RequestBody NoteDTO noteDTO) {
        try {
            if (noteDTO == null && (noteId == null || noteId.equals(""))) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.updateNote(noteId, noteDTO);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundExeption e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity deleteNote(@PathVariable ("noteId") String noteId) {
        try {
            noteService.deleteNote(noteId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundExeption e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
