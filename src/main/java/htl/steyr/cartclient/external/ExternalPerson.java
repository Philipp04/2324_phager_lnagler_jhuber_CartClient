package htl.steyr.cartclient.external;

import htl.steyr.cartclient.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("person")
public interface ExternalPerson {
    @GetMapping("/person/{token}")
    User getUser(@PathVariable String token);
}
