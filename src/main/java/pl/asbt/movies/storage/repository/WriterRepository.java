package pl.asbt.movies.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Writer;

import java.util.List;

@Transactional
@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {

    List<Writer> findByFirstnameAndAndSurname(String firstname, String surname);

    void deleteByFirstnameAndAndSurname(String firstname, String surname);
}
