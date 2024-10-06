package lk.ijse.gdse68.aad.NoteTakerV2.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String createNoteId() {
        return "NOTE : " + UUID.randomUUID();
    }
    public static String createUserId(){
        return "USER : " + UUID.randomUUID();
    }
    public static String toBase64ProfilePic(byte [] profilePic){
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
