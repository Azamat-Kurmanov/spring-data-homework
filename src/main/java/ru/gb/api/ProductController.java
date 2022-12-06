package ru.gb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Product;
import ru.gb.repository.ProductRepository;
import ru.gb.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private ProductService productService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }

    @GetMapping
    public List<Product> getProductList(){
        return productRepository.findAll();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        if (product!=null) {
            return productRepository.save(product);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    /**
     * Get list of products greater than min price
     * @return
     */

    @GetMapping("/greater")
    public List<Product> getProductListGreater(){
        return productService.getProductListGreaterThanMinPrice();
    }

    /**
     * Get list of products less than max price
     * @return
     */

    @GetMapping("/less")
    public List<Product> getProductListLess(){
        return productService.getProductListLessThanMaxPrice();
    }

    /**
     * Get list of products between min and max price
     * @return
     */

    @GetMapping("/between")
    public List<Product> getProductListBetween(){
        return productService.getProductListGreaterThanMinAndLessThanMaxPrice();
    }
}
