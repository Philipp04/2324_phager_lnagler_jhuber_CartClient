package htl.steyr.cartclient.service;

import htl.steyr.cartclient.dto.request.ProductRequestDto;
import htl.steyr.cartclient.dto.request.RemoveCartProductDto;
import htl.steyr.cartclient.dto.request.StockRequestDto;
import htl.steyr.cartclient.dto.response.CartProductResponseDto;
import htl.steyr.cartclient.dto.response.StockResponseDto;
import htl.steyr.cartclient.external.ExternalStock;
import htl.steyr.cartclient.model.Cart;
import htl.steyr.cartclient.model.CartProduct;
import htl.steyr.cartclient.model.Product;
import htl.steyr.cartclient.model.User;
import htl.steyr.cartclient.repository.CartProductRepository;
import htl.steyr.cartclient.repository.CartRepository;
import htl.steyr.cartclient.repository.ProductRepository;
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
    private CartProductRepository cartProductRepository;
    private ProductRepository productRepository;
    private ExternalStock externalStock;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
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
        if (externalStock.getStock(productRequestDto.id()).id() < productRequestDto.amount()) {
            Optional<Cart> c = cartRepository.findByUser(user);
            if (c.isPresent()) {
                Cart cart = c.get();
                ArrayList<CartProduct> cp = cart.getProducts();
                CartProduct cartProduct = new CartProduct();
                Optional<Product> product = productRepository.findById(productRequestDto.id());
                product.ifPresent(cartProduct::setProduct);
                cartProduct.setCart(cart);
                cartProduct.setAmount(productRequestDto.amount());
                cp.add(cartProduct);
                cart.setProducts(cp);
                cartProductRepository.save(cartProduct);
                cartRepository.save(cart);
                externalStock.removeStock(new StockRequestDto(productRequestDto.id(), productRequestDto.amount()));

            }
        }
    }

    public void removeFromCart(User user, RemoveCartProductDto removeCartProductDto) throws Exception {
        Cart cart = cartRepository.findByUser(user).orElseThrow(Exception::new);

        Optional<CartProduct> optionalCartProduct = cartProductRepository.findById(removeCartProductDto.id());

        if (optionalCartProduct.isPresent()) {
            CartProduct cartProduct = optionalCartProduct.get();
            if (removeCartProductDto.amount() < cartProduct.getAmount()) {
                cartProduct.setAmount(cartProduct.getAmount() - removeCartProductDto.amount());
                cartProductRepository.save(cartProduct);
                externalStock.addStock(new StockRequestDto(cartProduct.getProduct().getId(), removeCartProductDto.amount()));
            } else {
                cart.getProducts().remove(cartProduct);
                cartProductRepository.delete(cartProduct);
                externalStock.addStock(new StockRequestDto(cartProduct.getId(), cartProduct.getAmount()));
            }
            cartRepository.save(cart);
        }


    }

    public void clearCart(User user) {
        Optional<Cart> c = cartRepository.findByUser(user);
        if (c.isPresent()) {
            Cart cart = c.get();
            cart.getProducts().clear();
            cartRepository.save(cart);
            cartRepository.flush();
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
