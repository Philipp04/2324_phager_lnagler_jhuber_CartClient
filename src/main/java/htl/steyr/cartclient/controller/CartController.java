package htl.steyr.cartclient.controller;

import htl.steyr.cartclient.dto.request.ProductRequestDto;
import htl.steyr.cartclient.dto.response.CartProductResponseDto;
import htl.steyr.cartclient.dto.response.ProductResponseDto;
import htl.steyr.cartclient.external.ExternalPerson;
import htl.steyr.cartclient.model.User;
import htl.steyr.cartclient.service.CartService;
import jakarta.ws.rs.Path;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {


    private CartService cartService;
    private ExternalPerson externalPerson;

    public CartController(CartService cartService, ExternalPerson externalPerson) {
        this.cartService = cartService;
        this.externalPerson = externalPerson;
    }

    @GetMapping("/products")
    public ResponseEntity<List<CartProductResponseDto>> getProducts(){
        try{
            List<CartProductResponseDto> result = null;
            User user = externalPerson.getUser(cartService.getBearerToken());
            if(user != null) {
                result = cartService.getProducts(user);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/createCart")
    public ResponseEntity<String> createCart(){
        try{
            User user = externalPerson.getUser(cartService.getBearerToken());
            if(user != null) {
                cartService.createCart(user);
            }
            return new ResponseEntity("Cart was created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody ProductRequestDto productRequestDto){
        try{
            User user = externalPerson.getUser(cartService.getBearerToken());
            cartService.addToCart(user, productRequestDto);
            return new ResponseEntity<>("Added to Cart", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

}
