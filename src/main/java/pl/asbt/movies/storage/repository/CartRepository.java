package pl.asbt.movies.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Cart;

@Transactional
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
