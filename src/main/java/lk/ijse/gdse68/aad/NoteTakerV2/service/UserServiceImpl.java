package lk.ijse.gdse68.aad.NoteTakerV2.service;

import jakarta.transaction.Transactional;
import lk.ijse.gdse68.aad.NoteTakerV2.customObj.UserErrorResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.customObj.UserResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.UserDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.entity.UserEntity;
import lk.ijse.gdse68.aad.NoteTakerV2.exception.UserNotFoundExeption;
import lk.ijse.gdse68.aad.NoteTakerV2.repository.UserRepository;
import lk.ijse.gdse68.aad.NoteTakerV2.util.AppUtil;
import lk.ijse.gdse68.aad.NoteTakerV2.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final Mapping mapping;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public String saveUser(UserDTO userDTO) {
        userDTO.setUserId(AppUtil.createUserId());
        UserEntity savedUser = userRepository.save(mapping.convertToUserEntity(userDTO));
        if(savedUser != null && savedUser.getUserId() != null) {
            return "User saved successfully";
        }else {
            return "Save Failed !!";
        }
    }

    @Override
    public void updateUser(UserDTO incomingUserDTO) {
        Optional<UserEntity> tmpUser = userRepository.findById(incomingUserDTO.getUserId());
        if (!tmpUser.isPresent()) {
            throw new UserNotFoundExeption("User not found");
        }else {
            tmpUser.get().setFirstName(incomingUserDTO.getFirstName());
            tmpUser.get().setLastName(incomingUserDTO.getLastName());
            tmpUser.get().setEmail(incomingUserDTO.getEmail());
            tmpUser.get().setPassword(incomingUserDTO.getPassword());
            tmpUser.get().setProfilePic(incomingUserDTO.getProfilePic());
            //tmpUser.get().setNotes(incomingUserDTO.getNotes());
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> selectedUserId = userRepository.findById(userId);
        if (!selectedUserId.isPresent()) {
            throw new UserNotFoundExeption("User not found");
        }else {
            userRepository.deleteById(userId);
        }
    }

    @Override
    public UserResponse getSelectedUser(String userId) {
//        return mapping.convertToUserDTO(userRepository.getReferenceById(userId));
        if (userRepository.existsById(userId)) {
            UserEntity userEntityByUserId = userRepository.getUserEntityByUserId(userId);
            return mapping.convertToUserDTO(userEntityByUserId);
        }else {
            return new UserErrorResponse(0, "User not found");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.convertToUserDTOList(userRepository.findAll());
    }
}
