package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.OrderMapper;
import pl.asbt.movies.storage.servise.OrderService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderFacade {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public void createOrder(OrderDto orderDto) {
        orderService.saveOrder(orderMapper.mapToOrder(orderDto));
    }

    public OrderDto fetchOrder(Long orderId) throws StorageException {
        return orderMapper.mapToOrderDto(orderService.getOrder(orderId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no order with given id.")
                        .build()
        ));
    }

    public List<OrderDto> fetchOrders() {
        return orderMapper.mapToOrdersDto(orderService.getAllOrders());
    }

    public void deleteOrder(Long orderId) {
        orderService.deleteOrder(orderId);
    }

    public OrderDto finalizeOrder(Long orderId) {
        return orderMapper.mapToOrderDto(orderService.finalizeOrder(orderId));
    }
}
