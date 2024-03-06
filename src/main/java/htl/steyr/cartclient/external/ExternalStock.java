package htl.steyr.cartclient.external;

import htl.steyr.cartclient.dto.request.StockRequestDto;
import htl.steyr.cartclient.dto.response.StockResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("stock")
public interface ExternalStock {

    @GetMapping("/stock/load/{productId}")
    public StockResponseDto getStock(@PathVariable Long productId);

    @PostMapping("/stock/remove")
    public void removeStock(@RequestParam StockRequestDto stockRequestDto);

    @PostMapping("/stock/add")
        public void addStock(@RequestParam StockRequestDto stockRequestDto);

}

