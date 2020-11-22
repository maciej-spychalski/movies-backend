package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "USERS")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"FIRSTNAME", "SURNAME"})
})
public class User {
    private Long id;
    private String firstname;
    private String surname;
    private String email;
    private String password;
    private Boolean isAdmin;
    private Boolean isLoggedIn;
    private Cart cart;
    private List<Order> orders = new ArrayList<>();

    public User(String firstname, String surname, String email, String password, Boolean isAdmin, Boolean isLoggedIn) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isLoggedIn = isLoggedIn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "USER_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    @Column(name = "ISADMIN")
    public Boolean getAdmin() {
        return isAdmin;
    }

    @Column(name = "IS_LOGGED_IN")
    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CART_ID")
    public Cart getCart() {
        return cart;
    }

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Order> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
