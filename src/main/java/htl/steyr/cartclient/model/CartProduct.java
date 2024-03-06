package htl.steyr.cartclient.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart_products")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="amount")
    int amount;

    @ManyToOne
    @JoinColumn(name="cart_id")
    @NonNull
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NonNull
    Product product;



}
