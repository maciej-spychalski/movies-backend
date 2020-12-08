package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ItemDto {

    @NotNull(message = "Please provide valid item Id" )
    private Long id;
    @NotBlank(message = "Please provide valid movie title" )
    private String movieTitle;
    @NotNull(message = "Please provide valid movie id" )
    private Long movieId;
    @NotNull(message = "Please provide valid quantity" )
    private Integer quantity;
    private BigDecimal price;

}

