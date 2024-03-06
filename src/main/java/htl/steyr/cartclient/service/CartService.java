package htl.steyr.cartclient.service;

import htl.steyr.cartclient.dto.request.ProductRequestDto;
import htl.steyr.cartclient.dto.response.CartProductResponseDto;
import htl.steyr.cartclient.model.Cart;
import htl.steyr.cartclient.model.CartProduct;
import htl.steyr.cartclient.model.User;
import htl.steyr.cartclient.repository.CartProductRepository;
import htl.steyr.cartclient.repository.CartRepository;
import htl.steyr.cartclient.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductService productService;
    private CartProductService cartProductService;
    private UserRepository userRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartProductResponseDto> getProducts(User user) {
            Optional<Cart> c = cartRepository.findByUser(user);
            if (c.isPresent()) {
                return new ArrayList<>(c.get().getProducts().stream().map(CartProductService::map).toList());
            }
        return null;
    }

    public void createCart(User user) {
        if (cartRepository.findByUser(user).isEmpty()) {
            Cart c = new Cart();
            c.setUser(user);
            cartRepository.save(c);
            cartRepository.flush();
        }
    }

    public void addToCart(User user, ProductRequestDto productRequestDto) {
        Optional<Cart> c = cartRepository.findByUser(user);
        if (c.isPresent()) {
            List<CartProduct> cp = c.get().getProducts();

            //TODO: Dem Cart das Product hinzufügen
        }
    }

    public String getBearerToken() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        if (request.getHeader("Authorization") != null) {
            return request.getHeader("Authorization").replace("Bearer ", "");
        }
        return null;

    }


}
