package htl.steyr.cartclient.repository;

import htl.steyr.cartclient.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CartRepository extends JpaRepository<Long, Cart> {
}
