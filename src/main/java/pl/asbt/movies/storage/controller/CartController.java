package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.CartFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/carts")
public class CartController {

    private final CartFacade cartFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createCart(@Validated @RequestBody CartDto cartDto) {
        cartFacade.createCart(cartDto);
        // todo: To chyba można skasować bo koszyk tworzony jest wraz z użytkownikiem
    }

    @GetMapping(value = "/{cardId}")
    public CartDto getCard(@Validated @PathVariable Long cardId) throws StorageException {
        return cartFacade.fetchCard(cardId);
    }

    @GetMapping
    public List<CartDto> getCards() {
        return cartFacade.fetchCards();
    }

    @DeleteMapping(value = "/{cardId}")
    public void deleteCard(@Validated @PathVariable Long cardId) {
        cartFacade.deleteCard(cardId);
    }

    //    @PatchMapping(value = "/create-order/{cartId}/{userId}")
    //    @PutMapping(value = "/create-order/{cartId}/{userId}")
    @PostMapping(value = "/create-order/{cartId}/{userId}")
    public void createOrder(@Validated @PathVariable Long cartId,
                            @Validated @PathVariable Long userId) throws StorageException {
        cartFacade.createOrder(cartId, userId);
    }

    //        @PatchMapping(value = "/add-item/{cartId}/{itemId}")
    //    @PutMapping(value = "/add-item/{cartId}/{itemId}")
    @PostMapping(value = "/add-item/{cartId}/{itemId}")
    public CartDto addItem(@Validated @PathVariable Long cartId,
                           @Validated @PathVariable Long itemId) throws StorageException {
        return cartFacade.addItem(cartId, itemId);
    }

    //    @PatchMapping(value = "/delete-item/{cartId}/{itemId}")9
    //    @PutMapping(value = "/add-item/{cartId}/{itemId}")
    @PostMapping(value = "/delete-item/{cartId}/{itemId}")
    public CartDto deleteItem(@Validated @PathVariable Long cartId,
                              @Validated @PathVariable Long itemId) throws StorageException {
        return cartFacade.deleteItem(cartId, itemId);
    }

    //    @PatchMapping(value = "/update-price/{cartId}")
    //    @PutMapping(value = "/update-price/{cartId}")9
    @PostMapping(value = "/update-price/{cartId}")
    public CartDto updateCartPrice(@Validated @PathVariable Long cartId) throws StorageException {
        return cartFacade.updateCartPrice(cartId);
    }
}
