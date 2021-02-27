package pl.asbt.movies.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.OrderRepository;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final StorageItemRepository storageItemRepository;

    public Order saveOrder(final Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(final Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(final Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order finalizeOrder(final Long orderId) {
        Order result = new Order();
        try {
            Order order = getOrder(orderId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no order with given id.")
                            .build()
            );

            List<Item> items = order.getItems();
            for(Item theItem : items) {
                Movie movie = theItem.getMovie();
                int quantity = theItem.getQuantity();
                StorageItem storageItem = storageItemRepository.findByMovie_Title(movie.getTitle()).get(0);
                storageItem.setQuantity(storageItem.getQuantity() - quantity);
                storageItemRepository.save(storageItem);
            }

            order.setIsFinalized(true);
            order = orderRepository.save(order);
            return order;
        } catch (Exception e) {
            LOGGER.error("Order: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }
}
