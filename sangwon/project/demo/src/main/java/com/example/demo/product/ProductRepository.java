package com.example.demo.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private Map<Integer, Product> db = new HashMap<>();
    private int id = 1;

    public Product findProduct(int id) {
        System.out.println(db.get(id));
        return db.get(id);
    }

    public List<Product> findAllProduct() {
        return new ArrayList<>(db.values());
    }

    public void save(Product product) {
        System.out.println(product.getName());
        db.put(id++, product);
    }

}
