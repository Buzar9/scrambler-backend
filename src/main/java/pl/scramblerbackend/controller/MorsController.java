package pl.scramblerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.scramblerbackend.entity.MorsMessage;
import pl.scramblerbackend.entity.OutPassword;
import pl.scramblerbackend.service.MorsService;

import javax.validation.Valid;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/mors")
@CrossOrigin(origins = "*")
public class MorsController {

    @Autowired
    private MorsService morsService;

    @PostMapping
    public OutPassword encrypt(@RequestBody MorsMessage firstMessage) throws FileNotFoundException {

        return morsService.encrypt(firstMessage);
    }

}

