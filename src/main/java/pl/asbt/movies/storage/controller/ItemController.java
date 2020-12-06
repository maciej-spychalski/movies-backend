package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.ItemFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/items")
public class ItemController {

    private final ItemFacade itemFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ItemDto createItem(@Validated @RequestBody ItemDto itemDto) {
        return itemFacade.createItem(itemDto);
    }

    @GetMapping(value = "/{itemId}")
    public ItemDto getItem(@Validated @PathVariable Long itemId) throws StorageException {
        return itemFacade.fetchItem(itemId);
    }

//    @PatchMapping(value = "/add-quantity/{id}/{quantity}")
//    @PutMapping(value = "/add-quantity/{id}/{quantity}")
    @PostMapping(value = "/add-quantity/{id}/{quantity}")
    public ItemDto addQuantity(@Validated @PathVariable Long id,
                            @Validated @PathVariable int quantity) {
        return itemFacade.addQuantity(id, quantity);
    }

//    @PatchMapping(value = "/sub-quantity/{id}/{quantity}")
//    @PutMapping(value = "/sub-quantity/{id}/{quantity}")
    @PostMapping(value = "/sub-quantity/{id}/{quantity}")
    public ItemDto subQuantity(@Validated @PathVariable Long id,
                               @Validated @PathVariable int quantity) {
        return itemFacade.subQuantity(id, quantity);
    }

    @GetMapping
    public List<ItemDto> getItems() {
        return itemFacade.fetchItems();
    }

    @DeleteMapping(value = "/{itemId}")
    public void deleteItem(@Validated @PathVariable Long itemId) {
        itemFacade.deleteItem(itemId);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ItemDto updateItem(@Validated @RequestBody ItemDto itemDto) {
        return itemFacade.updateItem(itemDto);
    }
}
