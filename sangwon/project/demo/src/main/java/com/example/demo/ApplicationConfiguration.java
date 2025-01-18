package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.product.ProductRepository;

@Configuration
public class ApplicationConfiguration {
    @Bean // 아래 메소드가 반환하는 객체를 스프링 빈으로 등록해줘 (xml 방식)
    public ProductRepository productRepository() {
        return new ProductRepository();
    }
}
