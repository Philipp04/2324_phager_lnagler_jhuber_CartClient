package htl.steyr.cartclient.controller;

import htl.steyr.cartclient.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {


    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/products/{userId}")
    public List<ResponseEntity<Integer>> getProducts(){
        try{
            return new ResponseEntity<>()
        }catch(Exception e){
            return new ResponseEntity<>()
        }
    }

}
