package lk.ijse.gdse68.aad.NoteTakerV2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/api/v1/welcome")
public class WelcomeController {
    @GetMapping
    public String welcome(Model model){
        model.addAttribute("message", "Welcome to Note Controller V2 - 2024");
        return "welcome";
    }
}
