package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.CartMapper;
import pl.asbt.movies.storage.service.CartService;

import java.util.List;


@RequiredArgsConstructor
@Component
public class CartFacade {
    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartDto createCart(CartDto cartDto) {
        return cartMapper.mapToCartDto(cartService.saveCart(cartMapper.matToCart(cartDto)));
    }

    public CartDto fetchCard(Long cardId) throws StorageException {
        return cartMapper.mapToCartDto(cartService.getCart(cardId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no cart with given id.")
                        .build()
        ));
    }

    public List<CartDto> fetchCarts() {
        return cartMapper.mapToCartsDto(cartService.getAllCarts());
    }

    public void deleteCart(Long cardId) {
        cartService.deleteCart(cardId);
    }

    public void createOrder(Long cartId, Long userId) throws StorageException {
        cartService.createOrder(cartId, userId);
    }

    public CartDto addItem(Long cartId, Long itemId) throws StorageException {
        return cartMapper.mapToCartDto(cartService.addItem(cartId, itemId));
    }

    public CartDto deleteItem(Long cartId, Long itemId) throws StorageException {
        return cartMapper.mapToCartDto(cartService.deleteItem(cartId, itemId));
    }

    public CartDto updateCartPrice(Long cartId) throws StorageException {
        return cartMapper.mapToCartDto(cartService.updateCartPrice(cartId));
    }
}
