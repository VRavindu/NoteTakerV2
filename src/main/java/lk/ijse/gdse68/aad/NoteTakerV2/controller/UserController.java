package lk.ijse.gdse68.aad.NoteTakerV2.controller;

import lk.ijse.gdse68.aad.NoteTakerV2.customObj.UserResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.UserDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.exception.UserNotFoundExeption;
import lk.ijse.gdse68.aad.NoteTakerV2.repository.UserRepository;
import lk.ijse.gdse68.aad.NoteTakerV2.service.UserService;
import lk.ijse.gdse68.aad.NoteTakerV2.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    private final UserRepository userRepository;
    private UserDTO userDTO;

    //Save User
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic){

        try {
            byte [] imageByteCollection = profilePic.getBytes();
            String base64ProfilePic = AppUtil.toBase64ProfilePic(imageByteCollection);
            //build the user object
            UserDTO buildUserDTO = new UserDTO();
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(password);
            buildUserDTO.setProfilePic(base64ProfilePic);
            //sent to the service layer
            String saveStatus = userService.saveUser(buildUserDTO);
            if (saveStatus.contains("User saved successfully")){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(@PathVariable("id") String userId){
        return userService.getSelectedUser(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") String id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundExeption e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(
            @PathVariable ("userId") String userId,
            @RequestPart("firstName") String updateFirstName,
            @RequestPart("lastName") String updateLastName,
            @RequestPart("email") String updateEmail,
            @RequestPart("password") String updatePassword,
            @RequestPart("profilePic") MultipartFile updateProfilePic){
        try {
            byte [] imageByteCollection = updateProfilePic.getBytes();
            String updateBase64ProfilePic = AppUtil.toBase64ProfilePic(imageByteCollection);
            UserDTO updatedUserDTO = new UserDTO();
            updatedUserDTO.setUserId(userId);
            updatedUserDTO.setFirstName(updateFirstName);
            updatedUserDTO.setLastName(updateLastName);
            updatedUserDTO.setPassword(updatePassword);
            updatedUserDTO.setEmail(updateEmail);
            updatedUserDTO.setProfilePic(updateBase64ProfilePic);
            userService.updateUser(updatedUserDTO);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundExeption e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
