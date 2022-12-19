package ru.gb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartDto {
    private Product product;
    private Integer number;
}
