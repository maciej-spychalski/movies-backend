package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.UserDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.UserFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/users")
public class UserController {
    private final UserFacade userFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createUser(@Validated @RequestBody UserDto userDto) {
        userFacade.createUser(userDto);
    }

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@Validated @PathVariable Long userId) throws StorageException {
        return userFacade.fetchUser(userId);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userFacade.fetchUsers();
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@Validated @PathVariable Long userId) {
        userFacade.deleteUser(userId);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto updateUser(@Validated @RequestBody UserDto userDto) {
        return userFacade.updateUser(userDto);
    }

//    @PatchMapping(value = "/login/{email}/{password}")
//    @PutMapping(value = "/login/{email}/{password}")
    @PostMapping(value = "/login/{email}/{password}")
    public UserDto loginUser(@Validated @PathVariable String email,
                             @Validated @PathVariable String password) {
        return userFacade.loginUser(email, password);
    }

//    @PatchMapping(value = "/logout/{userId}")
//    @PutMapping(value = "/logout/{userId}")
    @PostMapping(value = "/logout/{userId}")
    public UserDto logoutUser(@Validated @PathVariable Long userId) {
        return userFacade.logoutUser(userId);
    }
}
