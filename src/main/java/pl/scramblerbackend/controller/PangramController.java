package pl.scramblerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;
import pl.scramblerbackend.service.PangramService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/pangram")
@CrossOrigin(origins = "*")
public class PangramController {

    @Autowired
    private PangramService pangramService;

    @PostMapping
    public OutPassword encrypt(@RequestBody KeyNMessage keyNMessage) throws FileNotFoundException {

        return pangramService.encrypt(keyNMessage);
    }

}
