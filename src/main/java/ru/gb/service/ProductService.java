package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.gb.model.Product;
import ru.gb.model.dto.ProductDto;
import ru.gb.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillProductOnStartApp(){
        productRepository.save(createProduct("Мука", 300.00));
        productRepository.save(createProduct("Картошка", 120.00));
        productRepository.save(createProduct("Лук", 350.00));
        productRepository.save(createProduct("Сыр", 480.00));
        productRepository.save(createProduct("Колбаса", 750.00));
        productRepository.save(createProduct("Молоко", 290.00));
        productRepository.save(createProduct("Тушенка", 540.00));
        productRepository.save(createProduct("Напиток", 200.00));
        productRepository.save(createProduct("Масло", 830.00));
        productRepository.save(createProduct("Варенье", 270.00));
        productRepository.save(createProduct("Компот", 960.00));
        productRepository.save(createProduct("Сгущенка", 370.00));
        productRepository.save(createProduct("Кофе", 840.00));
        productRepository.save(createProduct("Свекла", 760.00));
        productRepository.save(createProduct("Тыква", 650.00));
        productRepository.save(createProduct("Кабачки", 760.00));
        productRepository.save(createProduct("Огурец", 360.00));
        productRepository.save(createProduct("Помидор", 130.00));
        productRepository.save(createProduct("Арбуз", 790.00));
        productRepository.save(createProduct("Дыня", 540.00));
    }

    private Product createProduct(String title, Double price){
        Product product = null;
        if (title!=null && price!=null){
            product = new Product();
            product.setTitle(title);
            product.setPrice(price);
        }
        return product;
    }

    /**
     * Get list of products greater than min price
     * @return
     */
    public List<ProductDto> getProductListGreaterThanMinPrice(){
        List<ProductDto> productDto = new ArrayList<>();
        for (Product product: getProductListGreaterThanMin(productRepository.getMinPrice())){
            productDto.add(new ProductDto(product.getId(), product.getTitle(), product.getPrice()));
        }
        return productDto;
    }

    public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow();
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

    public List<ProductDto> getProductList(){
        List<ProductDto> productDto = new ArrayList<>();
        for (Product products: productRepository.findAll()){
            productDto.add(new ProductDto(products.getId(), products.getTitle(), products.getPrice()));
        }
        return productDto;
    }

    public ProductDto addProduct(Product product){
        Product savedObj = productRepository.save(product);
        return new ProductDto(savedObj.getId(), savedObj.getTitle(), savedObj.getPrice());
    }

    public ProductDto updateProduct(Product product){
        Product savedObj = productRepository.save(product);
        return new ProductDto(savedObj.getId(), savedObj.getTitle(), savedObj.getPrice());
    }

    /**
     * Get list of products less than max price
     * @return
     */
    public List<ProductDto> getProductListLessThanMaxPrice() {
        List<ProductDto> productDto = new ArrayList<>();
        for (Product products: getProductListLessThanMax(productRepository.getMaxPrice())){
            productDto.add(new ProductDto(products.getId(), products.getTitle(), products.getPrice()));
        }
        return productDto;
    }

    /**
     * Get list of products between min and max price
     * @return
     */
    public List<ProductDto> getProductListGreaterThanMinAndLessThanMaxPrice() {
        List<ProductDto> productDto = new ArrayList<>();
        for (Product products: getProductListBetweenMinAndMax(productRepository.getMinPrice(), productRepository.getMaxPrice())){
            productDto.add(new ProductDto(products.getId(), products.getTitle(), products.getPrice()));
        }
        return productDto;
    }

    private List<Product> getProductListGreaterThanMin(Double price){
        return productRepository.findAllByPriceGreaterThan(price);
    }

    private List<Product> getProductListLessThanMax(Double price){
        return productRepository.findAllByPriceLessThan(price);
    }

    private List<Product> getProductListBetweenMinAndMax(Double min, Double max){
        return productRepository.findAllByPriceGreaterThanAndPriceLessThan(min, max);
    }
}
