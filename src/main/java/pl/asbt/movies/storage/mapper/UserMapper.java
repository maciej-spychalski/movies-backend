package pl.asbt.movies.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.UserDto;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getFirstname(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getIsAdmin(),
                userDto.getIsLogged()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getIsAdmin(),
                user.getIsLogged(),
                cartMapper.mapToCartDto(user.getCart()),
                orderMapper.mapToOrdersDto(user.getOrders())
        );
    }
}
