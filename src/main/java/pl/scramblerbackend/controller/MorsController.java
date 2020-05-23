package pl.scramblerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.scramblerbackend.service.MorsService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/mors")
@CrossOrigin(origins = "*")
public class MorsController {

    @Autowired
    private MorsService morsService;

    @PostMapping
    public String encrypt(@RequestBody String inMessage) throws FileNotFoundException {
        return morsService.encrypt(inMessage);
    }

}

