package com.foo.edgeservice;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class EdgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeServiceApplication.class, args);
    }
}

@Data
class Product {

    private int Id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private double price;

}


@FeignClient("product-catalog-service")
interface ProductClient {

    @GetMapping("/products")
    Resources<Product> getProducts();

}


@RestController
class ProductPriceAggregationController {

    private final ProductClient productClient;

    public ProductPriceAggregationController( ProductClient productClient) {
        this.productClient = productClient;
    }

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/total")
    public Double aggregate () throws URISyntaxException {

        /*RequestEntity request = RequestEntity
                .get(new URI("http://192.168.1.130:8080/products")).build();
        ParameterizedTypeReference<ArrayList<Product>> typeRef = new ParameterizedTypeReference<ArrayList<Product>>() {
        };
        ResponseEntity<ArrayList<Product>> responseEntity = restTemplate().exchange(request, typeRef);
        List<Product> products = responseEntity.getBody();*/
        double total = 0.0;
        Iterator<Product> products = productClient.getProducts().getContent().iterator();

       while(products.hasNext()){
          Product p = products.next();
            total +=p.getPrice();
        }
        return total;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}