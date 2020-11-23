package pl.asbt.movies.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

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
        CartDto cartDto = null;
        if (user.getCart() != null) {
            cartDto = cartMapper.mapToCartDto(user.getCart());
        }
        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getIsAdmin(),
                user.getIsLogged(),
                cartDto,
//                cartMapper.mapToCartDto(user.getCart()),
                orderMapper.mapToOrdersDto(user.getOrders())
        );
    }

    public List<UserDto> mapToUsersDto(final List<User> users) {
        return users.stream()
                .map(u -> mapToUserDto(u))
                .collect(Collectors.toList());
    }
}
