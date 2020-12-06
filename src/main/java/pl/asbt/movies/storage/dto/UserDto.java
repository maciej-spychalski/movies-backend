package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserDto {

    @NotNull
    private Long id;
    @NotBlank(message = "Please provide firstname")
    private String firstname;
    @NotBlank(message = "Please provide surname")
    private String surname;
    @Email(message = "Please provide valid email")
    private String email;
    @NotBlank(message = "Please provide valid password")
    private String password;
    @NotNull(message = "Please provide true or false")
    private Boolean isAdmin;
    @NotNull(message = "Please provide true or false")
    private Boolean isLogged;
    private CartDto cartDto;
    private List<OrderDto> ordersDto = new ArrayList<>();
}
