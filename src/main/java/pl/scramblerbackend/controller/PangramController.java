package pl.scramblerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.scramblerbackend.entity.Pangram;
import pl.scramblerbackend.service.PangramService;

@RestController
@RequestMapping("/pangram")
@CrossOrigin(origins = "*")
public class PangramController {

    @Autowired
    private PangramService pangramService;

    @PostMapping
    public String encrypt(@RequestBody Pangram keyNMessage) {

        return pangramService.encrypt(keyNMessage);
    }

}
