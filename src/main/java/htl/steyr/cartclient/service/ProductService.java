package htl.steyr.cartclient.service;

import htl.steyr.cartclient.dto.request.ProductRequestDto;
import htl.steyr.cartclient.dto.response.ProductResponseDto;
import htl.steyr.cartclient.model.CartProduct;
import htl.steyr.cartclient.model.Product;
import htl.steyr.cartclient.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static ProductResponseDto map(Product p){
        return new ProductResponseDto(p.getName(),p.getDescription(), p.getPrice());
    }
}
