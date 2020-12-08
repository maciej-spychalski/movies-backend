package pl.asbt.movies.description.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.description.dto.DescriptionDto;
import pl.asbt.movies.description.service.DescriptionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/description")
public class DescriptionController {

    @Autowired
    private DescriptionService service;

    @GetMapping(value = "/{movieTitle}")
    public DescriptionDto getDescription(@PathVariable String movieTitle) {
        return service.getDescription(movieTitle);
    }
}
