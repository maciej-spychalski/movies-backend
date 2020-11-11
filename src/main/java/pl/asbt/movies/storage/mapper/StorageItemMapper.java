package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.StorageItem;
import pl.asbt.movies.storage.domain.StorageItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StorageItemMapper {

    public StorageItem mapToStorageItem(final StorageItemDto storageItemDto, Movie movie) {
        return new StorageItem(
                movie,
                storageItemDto.getQuantity());
    }

    public StorageItemDto mapToStorageItemDto(final StorageItem storageItem) {
        return new StorageItemDto(
                storageItem.getId(),
                storageItem.getMovie().getTitle(),
                storageItem.getMovie().getId(),
                storageItem.getQuantity());
    }

    public List<StorageItemDto> mapToStorageItemsDto(final List<StorageItem> storageItems) {
        return storageItems.stream()
                .map(i -> mapToStorageItemDto(i))
                .collect(Collectors.toList());
    }

}
