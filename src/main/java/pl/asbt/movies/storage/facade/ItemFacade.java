package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.ItemMapper;
import pl.asbt.movies.storage.servise.ItemService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemFacade {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ItemDto createItem(ItemDto itemDto) {
        return itemMapper.mapToItemDto(itemService.saveItem(itemDto));
    }

    public ItemDto fetchItem(Long itemId) throws StorageException {
        return itemMapper.mapToItemDto(itemService.getItem(itemId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no item with given id.")
                        .build()
        ));
    }

    public ItemDto addQuantity(Long id, int quantity) {
        return itemMapper.mapToItemDto(itemService.addQuantity(id, quantity));
    }

    public ItemDto subQuantity(Long id, int quantity) {
        return itemMapper.mapToItemDto(itemService.subQuantity(id, quantity));
    }

    public List<ItemDto> fetchItems() {
        return itemMapper.mapToItemsDto(itemService.getAllItems());
    }

    public void deleteItem(Long itemId) {
        itemService.deleteItem(itemId);
    }

    public ItemDto updateItem(ItemDto itemDto) {
        return itemMapper.mapToItemDto(itemService.updateItem(itemDto));
    }
}
