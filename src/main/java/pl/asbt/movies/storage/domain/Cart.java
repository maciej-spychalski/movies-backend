package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "CARTS")
public class Cart {
    private Long id;
    private List<Item> items;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "CART_ID", unique = true)
    public Long getId() {
        return id;
    }

    @OneToMany (
            targetEntity = Item.class,
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Item> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

