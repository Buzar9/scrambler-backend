package pl.scramblerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.scramblerbackend.entity.Sylabic;
import pl.scramblerbackend.service.SylabicService;

@RestController
@RequestMapping("/sylabic")
@CrossOrigin(origins = "*")
public class SylabicController {

    @Autowired
    private SylabicService sylabicService;

    @PostMapping
    public String encrypt(@RequestBody Sylabic keyNMessage) {

        return sylabicService.encrypt(keyNMessage);
    }
}
