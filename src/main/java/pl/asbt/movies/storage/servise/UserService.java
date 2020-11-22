package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    public List<User> getUserByNameAndSurname(final String firstname, final String surname) {
        return userRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    /*
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    */
}
