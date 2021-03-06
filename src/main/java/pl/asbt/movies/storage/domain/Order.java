package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "ORDERS")
public class Order {
    private Long id;
    private Boolean isFinalized = false;
    private List<Item> items = new ArrayList<>();
    private User user;
    private BigDecimal price = new BigDecimal(BigInteger.ZERO);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ORDER_ID", unique = true)
    public Long getId() {
        return id;
    }

    @OneToMany (
            targetEntity = Item.class,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Item> getItems() {
        return items;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    @NotNull
    @Column(name = "IS_FINALIZED")
    public Boolean getIsFinalized() {
        return isFinalized;
    }

    @Column(name = "PRICE")
    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIsFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}

