package com.foo;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogServiceApplication.class, args);
	}
}

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
class Product {
	
	public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
	
	@Id
	@GeneratedValue
	@Getter
	@Setter
	private int Id;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private double price;
	
}

@RepositoryRestResource
interface ProductRepository extends CrudRepository<Product, Integer> {

}
/*
@RepositoryRestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/all")
    public List<Product> getAll(){
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }

}*/

@Component
class ProductInitializer implements CommandLineRunner {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public void run(String... args) throws Exception {
		
		Stream.of(new Product("abc", 4.5), new Product("foo", 8.36)).
		forEach(product -> productRepo.save(product));
		
		productRepo.findAll().forEach(System.out::println);
	}
	
}

