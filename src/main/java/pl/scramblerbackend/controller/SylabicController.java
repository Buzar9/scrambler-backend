package pl.scramblerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;
import pl.scramblerbackend.service.SylabicService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/sylabic")
@CrossOrigin(origins = "*")
public class SylabicController {

    @Autowired
    private SylabicService sylabicService;

    @PostMapping
    public OutPassword encrypt(@RequestBody KeyNMessage keyNMessage) throws FileNotFoundException {

        return sylabicService.encrypt(keyNMessage);
    }
}
