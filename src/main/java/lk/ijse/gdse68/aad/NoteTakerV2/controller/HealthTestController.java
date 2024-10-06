package lk.ijse.gdse68.aad.NoteTakerV2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/healthtest")
public class HealthTestController {
    @GetMapping()
    public String healthCheck(){
        return "Note Taker Running Well !!";
    }
}
