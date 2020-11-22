package pl.asbt.movies.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Item;
import pl.asbt.movies.storage.dto.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    public Item mapToItem(final ItemDto itemDto) {
        return new Item(
                itemDto.getQuantity()
        );
    }

    public ItemDto mapToItemDto(final Item item) {
        return new ItemDto(
                item.getId(),
                item.getMovie().getTitle(),
                item.getQuantity()
        );
    }

    public List<ItemDto> mapToItemsDto(final List<Item> items) {
        return items.stream()
                .map(i -> mapToItemDto(i))
                .collect(Collectors.toList());
    }
}
