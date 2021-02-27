package pl.asbt.movies.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Cart;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.UserDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public User saveUser(final User user) {
        Cart cart = new Cart();
        user.setCart(cart);
        return userRepository.save(user);
    }

    public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUser(final String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUserByNameAndSurname(final String firstname, final String surname) {
        return userRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByFirstnameAndSurname(final String firstname, final String surname) {
        userRepository.deleteByFirstnameAndSurname(firstname, surname);
    }

    public User updateUser(final UserDto userDto) {
        User result = new User();
        Long userId = userDto.getId();
        try {
            User user = getUser(userId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no user with given id.")
                            .build()
            );
            user.setFirstname(userDto.getFirstname());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setIsAdmin(userDto.getIsAdmin());
            user.setIsLogged(userDto.getIsLogged());
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("User: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

    public User loginUser(final String email, final String password) {
        User result = new User();
        try {
            User user = getUser(email).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no user with given email.")
                            .build()
            );
            if (user.getEmail().equals(email) &&
                    user.getPassword().equals(password)) {
                user.setIsLogged(true);
            }
            ;
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("User: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

    public User logoutUser(final Long userId) {
        User result = new User();
        try {
            User user = getUser(userId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no user with given id.")
                            .build()
            );
            user.setIsLogged(false);
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("User: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }
}
