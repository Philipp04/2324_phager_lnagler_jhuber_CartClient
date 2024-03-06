package htl.steyr.cartclient.repository;

import htl.steyr.cartclient.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
