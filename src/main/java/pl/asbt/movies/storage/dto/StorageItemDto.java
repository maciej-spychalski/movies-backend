package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StorageItemDto {

    @NotNull(message = "Please provide valid storage item Id" )
    private Long id;
    @NotBlank(message = "Please provide valid movie title" )
    private String movieTitle;
    @NotNull(message = "Please provide valid movie id" )
    private Long movieId;
    @NotNull(message = "Please provide valid quantity" )
    private Integer quantity;
}
