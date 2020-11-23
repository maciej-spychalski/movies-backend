package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.OrderMapper;
import pl.asbt.movies.storage.servise.OrderService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createOrder(@Validated @RequestBody OrderDto orderDto) {
        orderService.saveOrder(orderMapper.mapToOrder(orderDto));
    }

    @GetMapping(value = "/{orderId}")
    public OrderDto getOrder(@Validated @PathVariable Long orderId) throws StorageException {
        return orderMapper.mapToOrderDto(orderService.getOrder(orderId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no order with given id.")
                        .build()
        ));
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderMapper.mapToOrdersDto(orderService.getAllOrders());
    }

    @DeleteMapping(value = "/{cardId}")
    public void deleteCard(@Validated @PathVariable Long orederId) {
        orderService.deleteOrder(orederId);
    }
}
