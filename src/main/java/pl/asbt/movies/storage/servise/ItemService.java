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
        return itemRepository.save(item);
    }

    public Boolean addQuantity(Long id, int quantity) {
        Item item = getItem(id).orElse(new Item());
        StorageItem storageItem = storageItemService.getStorageItemsByMovieTitle(item.getMovie().getTitle()).get(0);
        if (storageItem.getQuantity() >= item.getQuantity() + quantity) {
            item.setQuantity(item.getQuantity() + quantity);
            itemRepository.save(item);
            return true;
        } else {
            LOGGER.error("Too few movies: " + item.getMovie().getTitle());
        }
        return false;
    }

    public Boolean subQuantity(Long id, int quantity) {
        Boolean succeed = false;
        Item Item = getItem(id).orElse(new Item());
        int currentQuantity = Item.getQuantity();
        if (currentQuantity - quantity > 0) {
            Item.setQuantity(currentQuantity - quantity);
            succeed = true;
            itemRepository.save(Item);
        }
        return succeed;
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
            item.setQuantity(itemDto.getQuantity());
            return itemRepository.save(item);
        } catch (Exception e) {
            LOGGER.error("Item: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}
