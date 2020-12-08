package pl.asbt.movies.passwordgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.asbt.movies.passwordgenerator.dto.GeneratedPasswordDto;
import pl.asbt.movies.passwordgenerator.service.PasswordGeneratorService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/passwordGenerator")
public class PasswordGeneratorController {

    @Autowired
    private PasswordGeneratorService service;

    @GetMapping
    public GeneratedPasswordDto getPasswords() {
        return service.getPasswords();
    }

}
