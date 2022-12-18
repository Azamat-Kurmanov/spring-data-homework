package ru.gb.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.entities.Product;

public class ProductSpecifications {

    public static Specification<Product> priceGreaterOrEqualsThan(Double price){
        return ((root, createQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Product> priceLessOrEqualsThan(Double price){
        return ((root, createQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Product> titleLike(String title){
        return ((root, createQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%t%%", title)));
    }
}
