package com.example.demo.product;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProduct(int id) {
        return productRepository.findProduct(id);
    }

    public List<Product> findAllProduct() {
        return productRepository.findAllProduct();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

}