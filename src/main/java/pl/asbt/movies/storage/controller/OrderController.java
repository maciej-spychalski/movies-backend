package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.OrderFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createOrder(@Validated @RequestBody OrderDto orderDto) {
        orderFacade.createOrder(orderDto);
    }

    @GetMapping(value = "/{orderId}")
    public OrderDto getOrder(@Validated @PathVariable Long orderId) throws StorageException {
        return orderFacade.fetchOrder(orderId);
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderFacade.fetchOrders();
    }

    @DeleteMapping(value = "/{orderId}")
    public void deleteOrder(@Validated @PathVariable Long orderId) {
        orderFacade.deleteOrder(orderId);
    }

    @PostMapping(value = "/finalize/{orderId}")
    public OrderDto finalizeOrder(@Validated @PathVariable Long orderId) {
        return orderFacade.finalizeOrder(orderId);
    }
}
