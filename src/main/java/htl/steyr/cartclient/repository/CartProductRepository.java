package htl.steyr.cartclient.repository;

import htl.steyr.cartclient.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
