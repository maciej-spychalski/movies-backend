package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Cart;
import pl.asbt.movies.storage.domain.Item;
import pl.asbt.movies.storage.domain.Order;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.CartRepository;
import pl.asbt.movies.storage.repository.ItemRepository;
import pl.asbt.movies.storage.repository.OrderRepository;
import pl.asbt.movies.storage.repository.UserRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

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

    public Cart addItem(final Long cartId, final Long itemId) throws StorageException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no cart with given id.")
                        .build()
        );

        Item item = itemService.getItem(itemId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no item with given id.")
                        .build()
        );

        cart.getItems().add(item);
        item.setCart(cart);

        ///
        cart.setPrice(cart.getPrice().add(item.getPrice()));
        ///

        return cartRepository.save(cart);
    }

    public Cart deleteItem(final Long cartId, final Long itemId) throws StorageException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no cart with given id.")
                        .build()
        );

        Item item = itemService.getItem(itemId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no item with given id.")
                        .build()
        );

        ///
        cart.setPrice(cart.getPrice().subtract(item.getPrice()));
        ///

        cart.getItems().remove(item);
        itemService.deleteItem(itemId);
        return cartRepository.save(cart);
    }

    public Order createOrder(final Long cartId, final Long userId) throws StorageException {
        // todo: chyba wystarczy przekazać cart
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

        BigDecimal price = cart.getPrice();
        List<Item> itemsCart = cart.getItems();
        List<Item> itemsOrder = new ArrayList<>();
        cart.setPrice(new BigDecimal(BigInteger.ZERO));

        while (itemsCart.size() > 0) {
            Item theItem = itemsCart.get(0);
            Item item = new Item();
            item.setMovie(theItem.getMovie());
            item.setQuantity(theItem.getQuantity());
            itemsOrder.add(item);
            cart.getItems().remove(theItem);
            itemService.deleteItem(theItem.getId());
            cartRepository.save(cart);
        }

        Order order = new Order();
        order = orderRepository.save(order);
        order.setUser(user);
        order.setPrice(price);

        for (Item theItem : itemsOrder) {
            order.getItems().add(theItem);
            theItem.setOrder(order);
            orderRepository.save(order);
        }

        return order;
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

