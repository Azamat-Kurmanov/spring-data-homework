package ru.gb.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.converters.ProductConverter;
import ru.gb.dto.ProductCartDto;
import ru.gb.dto.ProductDto;
import ru.gb.entities.Product;
import ru.gb.repository.ProductRepository;
import ru.gb.service.ProductService;
import ru.gb.validators.ProductValidator;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return productConverter.entityToDto(product);
    }

    @GetMapping
    public List<ProductDto> getProductList(){
        return productService.getProductList();
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody Product product){
        ProductDto productDto = productConverter.entityToDto(product);
        productValidator.validate(productDto);
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody Product product){
        ProductDto productDto = productConverter.entityToDto(product);
        productValidator.validate(productDto);
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
            @RequestParam(name = "size_on_page", required = false) Integer sizeOnPage
    ){
        if (page<1) page = 1;
        return productService.find(minPrice, maxPrice, titlePart, page, sizeOnPage).map(p -> new ProductDto(p.getId(), p.getTitle(), p.getPrice()));
    }

    @PostMapping("/to_cart/{id}/{number}")
    public List<ProductCartDto> addProductToCart(@PathVariable Long id, @PathVariable Integer number){
        return productService.addProductToCart(id, number);
    }
}
