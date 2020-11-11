package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.StorageItem;

import java.util.List;
import java.util.Optional;

public interface StorageItemRepository extends CrudRepository<StorageItem, Long> {

    @Override
    StorageItem save(StorageItem storageItem);

    @Override
    Optional<StorageItem> findById(Long id);

    Optional<StorageItem> findByMovie_Title(String title);

    @Override
    List<StorageItem> findAll();

    @Override
    void deleteById(Long id);

    void deleteByMovie_Title(String title);
}
