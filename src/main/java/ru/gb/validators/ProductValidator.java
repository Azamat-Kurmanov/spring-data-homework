package ru.gb.validators;

import org.springframework.stereotype.Component;
import ru.gb.dto.ProductDto;
import ru.gb.exceptions.ValidationExceptions;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto){
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice() < 1){
            errors.add("Цена продукта не может быть меньше 1");
        }

        if (productDto.getTitle().isBlank()){
            errors.add("Продукт не может иметь пустое название");
        }

        if (!errors.isEmpty()){
            throw new ValidationExceptions(errors);
        }
    }
}
