package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.UserDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.UserMapper;
import pl.asbt.movies.storage.servise.UserService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createUser(@Validated @RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@Validated @PathVariable Long userId) throws StorageException {
        return userMapper.mapToUserDto(userService.getUser(userId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no user with given id.")
                        .build()
        ));
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userMapper.mapToUsersDto(userService.getAllUsers());
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@Validated @PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto updateUser(@Validated @RequestBody UserDto userDto) {
        return userMapper.mapToUserDto(userService.updateUser(userDto));
    }

    @PatchMapping(value = "/login/{userId}/{email}/{password}")
    public UserDto loginUser(@Validated @PathVariable Long userId,
                             @Validated @PathVariable String email,
                             @Validated @PathVariable String password) {
        return userMapper.mapToUserDto(userService.loginUser(userId, email, password));
    }

    @PatchMapping(value = "/logout/{userId}")
    public UserDto logoutUser(@Validated @PathVariable Long userId) {
        return userMapper.mapToUserDto(userService.logoutUser(userId));
    }

}
