package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StorageItemDto {
    @NotNull
    private Long id;
    @NotBlank
    private String movieTitle;
    @NotNull
    private Long movieId;
    @NotNull
    private Integer quantity;
}
