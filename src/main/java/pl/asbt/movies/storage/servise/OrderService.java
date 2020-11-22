package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Order;
import pl.asbt.movies.storage.repository.OrderRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    public Order saveOrder(final Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(final Long id) {
        return orderRepository.findById(id);
    }

    /*
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    */

}
