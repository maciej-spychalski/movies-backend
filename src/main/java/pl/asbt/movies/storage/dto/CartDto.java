package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class CartDto {

    @NotNull
    private Long id;
    private List<ItemDto> itemsDto;
}
