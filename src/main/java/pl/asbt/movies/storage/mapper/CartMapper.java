package pl.asbt.movies.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Cart;
import pl.asbt.movies.storage.dto.CartDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CartMapper {

    private final ItemMapper itemMapper;

    public Cart matToCart(final CartDto cartDto) {
        return new Cart();
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                itemMapper.mapToItemsDto(cart.getItems())
        );
    }

    public List<CartDto> mapToCartsDto(final List<Cart> carts) {
        return carts.stream()
                .map(c -> mapToCartDto(c))
                .collect(Collectors.toList());
    }
}
