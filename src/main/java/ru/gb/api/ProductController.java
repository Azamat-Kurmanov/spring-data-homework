package ru.gb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.entities.Product;
import ru.gb.dto.ProductDto;
import ru.gb.repository.ProductRepository;
import ru.gb.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private ProductRepository productRepository;
    private ProductService productService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductDto> getProductList(){
        return productService.getProductList();
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    /**
     * Get list of products greater than min price
     * @return
     */

    @GetMapping("/greater")
    public List<ProductDto> getProductListGreater(){
        return productService.getProductListGreaterThanMinPrice();
    }

    /**
     * Get list of products less than max price
     * @return
     */

    @GetMapping("/less")
    public List<ProductDto> getProductListLess(){
        return productService.getProductListLessThanMaxPrice();
    }

    /**
     * Get list of products between min and max price
     * @return
     */

    @GetMapping("/between")
    public List<ProductDto> getProductListBetween(){
        return productService.getProductListGreaterThanMinAndLessThanMaxPrice();
    }

    @GetMapping("/paging")
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "size_on_page", required = true) Integer sizeOnPage
    ){
        if (page<1) page = 1;
        return productService.find(minPrice, maxPrice, titlePart, page, sizeOnPage).map(s -> new ProductDto(s));
    }
}
