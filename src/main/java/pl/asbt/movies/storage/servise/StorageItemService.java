package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.CreatingException;
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
    private MovieService movieService;

    @Autowired
    public StorageItemService(StorageItemRepository storageItemRepository, StorageItemMapper storageItemMapper, MovieService movieService) {
        this.storageItemRepository = storageItemRepository;
        this.storageItemMapper = storageItemMapper;
        this.movieService = movieService;
    }

    public StorageItem createStorageItem(final StorageItemDto storageItemDto) throws SearchingException {
        Movie movie = movieService.getMovie(storageItemDto.getMovieId()).orElseThrow(SearchingException::new);
        StorageItem result = new StorageItem();
        try {
            return storageItemRepository.save(storageItemMapper.mapToStorageItem(storageItemDto, movie));
        } catch (Exception e) {
            LOGGER.error(CreatingException.ERR_STORAGE_ITEM_ALREADY_EXIST);
        }
        return result;
    }

    public Optional<StorageItem> getStorageItem(final Long id) {
        return storageItemRepository.findById(id);
    }

    public List<StorageItem> getStorageItemsByMovieTitle(final String title) {
        return storageItemRepository.findByMovie_Title(title);
    }

    public List<StorageItem> getAllStorageItems() {
        return storageItemRepository.findAll();
    }

    public void deleteStorageItem(final Long id) {
        storageItemRepository.deleteById(id);
    }

    public void deleteStorageItemByMovieTitle(final String title) {
        storageItemRepository.deleteByMovie_Title(title);
    }

    public void updateStorageItem(final StorageItemDto storageItemDto) throws SearchingException {
        Movie movie = movieService.getMovie(storageItemDto.getMovieId()).orElseThrow(SearchingException::new);
        Long id = storageItemDto.getId();
        try {
            StorageItem storageItem = getStorageItem(id).orElseThrow(SearchingException::new);
            storageItem.setMovie(movie);
            storageItem.setQuantity(storageItemDto.getQuantity());
            storageItemRepository.save(storageItem);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_STORAGE_ITEM);
        }
    }

}
