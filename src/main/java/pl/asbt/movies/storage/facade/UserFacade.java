package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.UserDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.UserMapper;
import pl.asbt.movies.storage.servise.UserService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;

    public void createUser(UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    public UserDto fetchUser(Long userId) throws StorageException {
        return userMapper.mapToUserDto(userService.getUser(userId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no user with given id.")
                        .build()
        ));
    }

    public List<UserDto> fetchUsers() {
        return userMapper.mapToUsersDto(userService.getAllUsers());
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public UserDto updateUser(UserDto userDto) {
        return userMapper.mapToUserDto(userService.updateUser(userDto));
    }

    public UserDto loginUser(String email, String password) {
        return userMapper.mapToUserDto(userService.loginUser(email, password));
    }

    public UserDto logoutUser(Long userId) {
        return userMapper.mapToUserDto(userService.logoutUser(userId));
    }
}
