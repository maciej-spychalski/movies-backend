package pl.asbt.movies.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Order;
import pl.asbt.movies.storage.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final ItemMapper itemMapper;

    public Order mapToOrder(final OrderDto orderDto) {
        return new Order();
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getIsFinalized(),
                itemMapper.mapToItemsDto(order.getItems()),
                order.getPrice()
        );
    }

    public List<OrderDto> mapToOrdersDto(final List<Order> orders) {
        return orders.stream()
                .map(o -> mapToOrderDto(o))
                .collect(Collectors.toList());
    }

}
