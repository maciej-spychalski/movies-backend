package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StorageItemDto {
    private Long id;
    private String movieTitle;
    private Long movieId;
    private Integer quantity;
}
