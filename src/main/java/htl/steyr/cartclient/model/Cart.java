package htl.steyr.cartclient.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Table(name="carts")
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    @NonNull
    User user;

    @OneToMany(mappedBy = "cart")
    ArrayList<CartProduct> products;
}
