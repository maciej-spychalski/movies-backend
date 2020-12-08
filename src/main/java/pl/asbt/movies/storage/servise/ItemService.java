package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Item;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.StorageItem;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.ItemMapper;
import pl.asbt.movies.storage.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final MovieService movieService;
    private final StorageItemService storageItemService;

    public Item saveItem(final ItemDto itemDto) {
        Movie movie = movieService.getMovie(itemDto.getMovieId()).orElse(
                movieService.getAllMoviesByTitle(itemDto.getMovieTitle()).get(0));
        Item item = itemRepository.save(itemMapper.mapToItem(itemDto));
        item.setMovie(movie);

        StorageItem storageItem = storageItemService.getStorageItemsByMovieTitle(item.getMovie().getTitle()).get(0);
        if (storageItem.getQuantity() < item.getQuantity()) {
            item.setQuantity(storageItem.getQuantity());
            LOGGER.error("Too few movies: " + item.getMovie().getTitle());
        }

        item.setPrice(movie.getPrice().multiply(new BigDecimal(item.getQuantity())));

        return itemRepository.save(item);
    }

    public Item addQuantity(Long id, int quantity) {
        Item item = getItem(id).orElse(new Item());
        StorageItem storageItem = storageItemService.getStorageItemsByMovieTitle(item.getMovie().getTitle()).get(0);
        if (storageItem.getQuantity() >= item.getQuantity() + quantity) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item.setQuantity(storageItem.getQuantity());

            LOGGER.error("Too few movies: " + item.getMovie().getTitle());
        }

        try {
            Movie movie = movieService.getMovie(item.getMovie().getId()).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no movie with given id.")
                            .build()
            );
            item.setPrice(movie.getPrice().multiply(new BigDecimal(item.getQuantity())));
        } catch (Exception e) {
            LOGGER.error("Movie: " + ErrorType.NOT_FOUND.name());
        }

        return itemRepository.save(item);
    }

    public Item subQuantity(Long id, int quantity) {
        Item item = getItem(id).orElse(new Item());
        int currentQuantity = item.getQuantity();
        if (currentQuantity - quantity > 0) {
            item.setQuantity(currentQuantity - quantity);
        } else {
            item.setQuantity(0);
        }

        try {
            Movie movie = movieService.getMovie(item.getMovie().getId()).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no movie with given id.")
                            .build()
            );
            item.setPrice(movie.getPrice().multiply(new BigDecimal(item.getQuantity())));
        } catch (Exception e) {
            LOGGER.error("Movie: " + ErrorType.NOT_FOUND.name());
        }

        return itemRepository.save(item);
    }

    public Optional<Item> getItem(final Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(final Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(final ItemDto itemDto) {
        Item result = new Item();
        Long itemId = itemDto.getId();
        try {
            Item item = getItem(itemId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no item with given id.")
                            .build()
            );

            StorageItem storageItem = storageItemService.getStorageItemsByMovieTitle(item.getMovie().getTitle()).get(0);
            if (storageItem.getQuantity() < itemDto.getQuantity()) {
                item.setQuantity(storageItem.getQuantity());
                LOGGER.error("Too few movies: " + item.getMovie().getTitle());
            } else {
                item.setQuantity(itemDto.getQuantity());
            }

            try {
                Movie movie = movieService.getMovie(item.getMovie().getId()).orElseThrow(() ->
                        StorageException.builder()
                                .errorType(ErrorType.NOT_FOUND)
                                .message("There are no movie with given id.")
                                .build()
                );
                item.setPrice(movie.getPrice().multiply(new BigDecimal(item.getQuantity())));
            } catch (Exception e) {
                LOGGER.error("Movie: " + ErrorType.NOT_FOUND.name());
            }

            return itemRepository.save(item);
        } catch (Exception e) {
            LOGGER.error("Item: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }
}
