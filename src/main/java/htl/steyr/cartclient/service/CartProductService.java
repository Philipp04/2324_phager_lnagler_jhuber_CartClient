package htl.steyr.cartclient.service;

import htl.steyr.cartclient.dto.response.CartProductResponseDto;
import htl.steyr.cartclient.dto.response.ProductResponseDto;
import htl.steyr.cartclient.model.CartProduct;
import htl.steyr.cartclient.repository.CartProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartProductService {

    private CartProductRepository cartProductRepository;

    public CartProductService(CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
    }

    public static CartProductResponseDto map(CartProduct cp){
        return new CartProductResponseDto(cp.getAmount(), ProductService.map(cp.getProduct()));
    }
}
