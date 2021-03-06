package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderDto {

    @NotNull(message = "Please provide valid order Id" )
    private Long id;
    private Boolean isFinalized;
    private List<ItemDto> itemsDto = new ArrayList<>();
    private BigDecimal price;

}


