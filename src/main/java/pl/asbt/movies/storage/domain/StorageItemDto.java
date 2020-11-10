package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StorageItemDto {
    private Long id;
    private MovieDto movieDto;
    private Integer quantity;
}
