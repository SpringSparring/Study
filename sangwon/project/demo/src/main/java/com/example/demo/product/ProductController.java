package com.example.demo.product;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 컨트롤러로 사용한다고 선언
// @Controller
// 응답으로 화면이 아닌 데이터를 반환한다고 선언 (REST API)
// @ResponseBody
@RestController
// @RestController = @Controller+@ResponseBody
public class ProductController {
    // 사용자(화면) 요청을 던지면 그걸 받아서
    // 요청에 맞는 메소드를 실행.
    // 그리고 그 안에서 그에 맞는 로직을 수행할 수 있도록 서비스에게 전달

    // DI (의존성 주입), productSevice 변수에 넣을 ProductService를 찾아서 넣어줘.
    @Autowired
    private ProductService productService;

    // @RequestMapping(value = "/products/", method = RequestMethod.GET)
    // @PathVariable => 가변적인 id값을 받는다.
    @GetMapping("/products")
    public List<Product> findAllProduct() {

        return productService.findAllProduct();
    }

    // 상품 조회
    // 로컬호스트는 생략 가능 value = "http://localhost:8080/"
    // @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    // @PathVariable => 가변적인 id값을 받는다.
    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable("id") int id) {
        // ProductService productService = new ProductService();
        return productService.findProduct(id);

        // return "Nodebook";
    }

    // 상품 등록
    // @RequestMapping(value = "/products", method = RequestMethod.POST)
    // // @RequestParam(value = "name") String productName => name이라는 값을 쿼리스트링으로 받아서
    // // productName에 저장하기.
    // public void saveProduct(@RequestParam(value = "name") String productName) {
    // productService.saveProduct(productName);
    // }

    // @RequestMapping(value = "/products", method = RequestMethod.POST)
    // RequestBody => http로 값이 올 때 body의 값을 지정한 객체에 넣어줌
    @PostMapping("/products")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

}
