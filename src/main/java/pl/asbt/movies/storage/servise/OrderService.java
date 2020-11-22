package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Order;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.OrderRepository;

import java.util.List;
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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(final Long id) {
        orderRepository.deleteById(id);
    }

//    public Order updateItem(final OrderDto orderDto) {
//        Order result = new Order();
//        Long orderId = orderDto.getId();
//        try {
//            Order order = getOrder(orderId).orElseThrow(() ->
//                    StorageException.builder()
//                            .errorType(ErrorType.NOT_FOUND)
//                            .message("There are no order with given id.")
//                            .build()
//            );
//            return saveOrder(order);
//        } catch (Exception e) {
//            LOGGER.error("Order: " + ErrorType.NOT_FOUND.name());
//        }
//        return result;
//    }

}
