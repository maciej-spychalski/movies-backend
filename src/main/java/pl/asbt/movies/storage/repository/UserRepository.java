package pl.asbt.movies.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.User;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // todo: chyba do usunięcia
    List<User> findByFirstnameAndAndSurname(String firstname, String surname);

    Optional<User> findByEmail(String  email);

    void deleteByFirstnameAndSurname(String firstname, String surname);
}
