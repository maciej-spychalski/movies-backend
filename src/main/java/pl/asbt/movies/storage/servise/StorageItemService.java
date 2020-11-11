package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.StorageItem;
import pl.asbt.movies.storage.domain.StorageItemDto;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.repository.MovieRepository;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StorageItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageItemService.class);
    private StorageItemRepository storageItemRepository;
    private StorageItemMapper storageItemMapper;
    private MovieRepository movieRepository;

    @Autowired
    public StorageItemService(StorageItemRepository storageItemRepository, StorageItemMapper storageItemMapper, MovieRepository movieRepository) {
        this.storageItemRepository = storageItemRepository;
        this.storageItemMapper = storageItemMapper;
        this.movieRepository = movieRepository;
    }

    public StorageItem createStorageItem(final StorageItemDto storageItemDto) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(storageItemDto.getMovieId()).orElseThrow(MovieNotFoundException::new);
        return storageItemRepository.save(storageItemMapper.mapToStorageItem(storageItemDto, movie));
    }

    public Optional<StorageItem> getStorageItem(final Long id) {
        return storageItemRepository.findById(id);
    }

    public Optional<StorageItem> getStorageItem(final String title) {
        return storageItemRepository.findByMovie_Title(title);
    }

    public List<StorageItem> getAllStorageItems() {
        return storageItemRepository.findAll();
    }

    public void deleteStorageItem(final Long id) {
        storageItemRepository.deleteById(id);
    }

    public void deleteStorageItem(final String title) {
        storageItemRepository.deleteByMovie_Title(title);
    }

    public void updateStorageItem(final StorageItemDto storageItemDto) {
        Long id = storageItemDto.getId();
        try {
            StorageItem storageItem = getStorageItem(id).orElseThrow(SearchingException::new);
            storageItem.setQuantity(storageItemDto.getQuantity());
            storageItemRepository.save(storageItem);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_STORAGE_ITEM);
        }
    }

}
