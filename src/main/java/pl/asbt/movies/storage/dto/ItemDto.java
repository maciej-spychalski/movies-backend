package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.asbt.movies.storage.domain.Cart;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.Order;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ItemDto {

    @NotNull
    private Long id;
    @NotNull
    private Movie movie;
    private Integer quantity;
    private List<Cart> carts = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
}

