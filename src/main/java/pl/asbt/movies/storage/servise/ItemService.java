package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Item;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    public Item saveItem(final Item item) {
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
            item.setQuantity(itemDto.getQuantity());
            return saveItem(item);
        } catch (Exception e) {
            LOGGER.error("Item: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}
