package ru.gb.converters;

import org.springframework.stereotype.Component;
import ru.gb.dto.ProductDto;
import ru.gb.entities.Product;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto){
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product){
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
