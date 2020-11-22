package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.ItemMapper;
import pl.asbt.movies.storage.servise.ItemService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createItem(@Validated @RequestBody ItemDto itemDto) {
        itemService.saveItem(itemDto);
    }

    @GetMapping(value = "/{itemId}")
    public ItemDto getItem(@Validated @PathVariable Long itemId) throws StorageException {
        return itemMapper.mapToItemDto(itemService.getItem(itemId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no item with given id.")
                        .build()
        ));
    }

    @PatchMapping(value = "/add-quantity/{id}/{quantity}")
    public void addQuantity(@Validated @PathVariable Long id,
                            @Validated @PathVariable int quantity) {
        itemService.addQuantity(id, quantity);
    }

    @PatchMapping(value = "/sub-quantity/{id}/{quantity}")
    public Boolean subQuantity(@Validated @PathVariable Long id,
                               @Validated @PathVariable int quantity) {
        return itemService.subQuantity(id, quantity);
    }

    @GetMapping
    public List<ItemDto> getItems() {
        return itemMapper.mapToItemsDto(itemService.getAllItems());
    }

    @DeleteMapping(value = "/{itemId}")
    public void deleteItem(@Validated @PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ItemDto updateItem(@Validated @RequestBody ItemDto itemDto) {
        return itemMapper.mapToItemDto(itemService.updateItem(itemDto));
    }
}
