package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class CartDto {

    @NotNull(message = "Please provide valid cart Id" )
    private Long id;
    private List<ItemDto> itemsDto;
    private BigDecimal price;
}
