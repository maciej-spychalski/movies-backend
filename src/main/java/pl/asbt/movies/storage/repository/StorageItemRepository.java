package pl.asbt.movies.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.StorageItem;

import java.util.List;

@Transactional
@Repository
public interface StorageItemRepository extends JpaRepository<StorageItem, Long> {

    List<StorageItem> findByMovie_Title(String title);

    void deleteByMovie_Title(String title);
}
