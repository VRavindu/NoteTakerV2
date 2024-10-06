package lk.ijse.gdse68.aad.NoteTakerV2.dto.impl;

import lk.ijse.gdse68.aad.NoteTakerV2.customObj.NoteResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoteDTO implements SuperDTO, NoteResponse {
    private String id;
    private String noteTitle;
    private String noteDesc;
    private String priorityLevel;
    private String createDate;
    private String userId;
}
