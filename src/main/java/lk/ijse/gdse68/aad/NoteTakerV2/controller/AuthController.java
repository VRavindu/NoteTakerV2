package lk.ijse.gdse68.aad.NoteTakerV2.controller;

import lk.ijse.gdse68.aad.NoteTakerV2.dto.impl.UserDTO;
import lk.ijse.gdse68.aad.NoteTakerV2.jwtmodels.JWTResponse;
import lk.ijse.gdse68.aad.NoteTakerV2.jwtmodels.SignIn;
import lk.ijse.gdse68.aad.NoteTakerV2.service.UserService;
import lk.ijse.gdse68.aad.NoteTakerV2.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;
    @PostMapping(value = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JWTResponse> signup(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic){
        try {
            byte [] imageByteCollection = profilePic.getBytes();
            String base64ProfilePic = AppUtil.toBase64ProfilePic(imageByteCollection);
            UserDTO buildUserDTO = new UserDTO();
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(password);
            buildUserDTO.setProfilePic(base64ProfilePic);
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
    @PostMapping(value = "signin")
    public ResponseEntity<JWTResponse> signin(@RequestBody SignIn signIn){
        return null;
    }
}
