package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Cart;
import pl.asbt.movies.storage.domain.Order;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final UserService userService;
    private final OrderService orderService;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCart(final Long id) {
        return cartRepository.findById(id);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public void deleteCart(final Long id) {
        cartRepository.deleteById(id);
    }

    public Order createOrder(final Long cartId, final Long userId) throws StorageException {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no cart with given id.")
                        .build()
        );
        User user = userService.getUser(userId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no user with given id.")
                        .build()
        );

        Order order = new Order();
        order.setUser(user);
        order.setItems(cart.getItems());
        cart.getItems().clear();
        user.getOrders().add(order);
        return orderService.saveOrder(order);
    }

//    public Cart updateCart(final CartDto cartDto) {
//        Cart result = new Cart();
//        Long cartId = cartDto.getId();
//        try {
//            Cart cart = getCart(cartId).orElseThrow(() ->
//                    StorageException.builder()
//                            .errorType(ErrorType.NOT_FOUND)
//                            .message("There are no cart with given id.")
//                            .build()
//            );
//         } catch (Exception e) {
//            LOGGER.error("Cart " + ErrorType.NOT_FOUND.name());
//        }
//
//        return result;
//    }

}

