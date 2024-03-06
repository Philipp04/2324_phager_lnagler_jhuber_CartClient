package htl.steyr.cartclient.repository;

import htl.steyr.cartclient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
