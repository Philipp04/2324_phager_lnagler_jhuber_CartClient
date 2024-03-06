package htl.steyr.cartclient.repository;

import htl.steyr.cartclient.model.Cart;
import htl.steyr.cartclient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
