package pl.asbt.movies.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Cart;
import pl.asbt.movies.storage.domain.Item;
import pl.asbt.movies.storage.domain.Order;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.ItemMapper;
import pl.asbt.movies.storage.repository.CartRepository;
import pl.asbt.movies.storage.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
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


        List<Item> items = cart.getItems();
        Boolean isItemExist = false;
        for (Item theItem : items) {
            if (theItem.getMovie().getTitle().equals(item.getMovie().getTitle())) {
                theItem.setQuantity(theItem.getQuantity() + item.getQuantity());
                cart.setPrice(cart.getPrice().add(item.getPrice()));
                ItemDto itemDto = itemMapper.mapToItemDto(theItem);
                item = itemService.updateItem(itemDto);
                isItemExist = true;
            }
        }
        if (!isItemExist) {
            cart.getItems().add(item);
            item.setCart(cart);
            cart.setPrice(cart.getPrice().add(item.getPrice()));
        }
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

        cart.setPrice(cart.getPrice().subtract(item.getPrice()));

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
        cart.setPrice(new BigDecimal(0));

        while (itemsCart.size() > 0) {
            Item theItem = itemsCart.get(0);
            Item item = new Item();
            item.setMovie(theItem.getMovie());
            item.setQuantity(theItem.getQuantity());
            item.setPrice(theItem.getPrice());
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

    public Cart updateCartPrice(final Long cartId) throws StorageException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no cart with given id.")
                        .build()
        );

        BigDecimal price = new BigDecimal(0);
        List<Item> items = cart.getItems();
        for (Item theItem : items) {
            price = price.add(theItem.getPrice());
        }
        cart.setPrice(price);
        return cartRepository.save(cart);
    }

}

