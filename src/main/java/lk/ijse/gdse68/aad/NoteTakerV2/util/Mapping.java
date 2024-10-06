package lk.ijse.gdse68.aad.NoteTakerV2.util;

import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.NoteDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.UserDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.entity.NoteEntity;
import lk.ijse.gdse68.aad.NoteTakerV2.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    // matters of noteEntity and DTO
    public NoteDTO convertToDTO(NoteEntity noteEntity) {
        return modelMapper.map(noteEntity, NoteDTO.class);
    }
    public NoteEntity convertToEntity(NoteDTO noteDTO) {
        return modelMapper.map(noteDTO, NoteEntity.class);
    }
    public List<NoteDTO> convertToDTO(List<NoteEntity> notes) {
        return modelMapper.map(notes, new TypeToken<List<NoteDTO>>(){}.getType());
    }

    //User Mapping matter
    public UserDTO convertToUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public UserEntity convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
    public List<UserDTO> convertToUserDTOList(List<UserEntity> userEntities) {
        return modelMapper.map(userEntities, new TypeToken<List<UserDTO>>() {}.getType());
    }
}
