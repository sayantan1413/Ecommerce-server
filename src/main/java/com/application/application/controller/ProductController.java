package com.application.application.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.application.dto.ProductDto;
import com.application.application.model.Product;
import com.application.application.services.ProductService;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProductController {

    // @Autowired
    // private ProductDao productDao;
    @Autowired
    private ProductService productService;

    @GetMapping("/list/{page}")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable int page) {
        try {
            return new ResponseEntity<List<ProductDto>>(this.productService.getProducts(page),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/list/{page}")
    public ResponseEntity<List<ProductDto>> getProductsByUser(@RequestHeader("email") String email,
            @PathVariable int page) {
        try {
            return new ResponseEntity<List<ProductDto>>(this.productService.getProductByUser(email, page),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/api/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product,
            @RequestHeader("email") String email) {
        try {
            URI uri = URI
                    .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/add").toUriString());
            productService.addProduct(product, email);
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/api/update")
    public ResponseEntity<?> updateLoad(@RequestBody ProductDto productDto,
            @RequestHeader("email") String email) {
        try {
            productService.updateProduct(productDto, email);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @DeleteMapping("/api/{productId}")
    public ResponseEntity<?> deleteCourse(@PathVariable long productId,
            @RequestHeader("email") String email) {
        try {
            productService.deleteProduct(productId, email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
