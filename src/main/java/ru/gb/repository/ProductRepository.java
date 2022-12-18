package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT d FROM product d WHERE d.price > (SELECT MIN(c.price) FROM product)");

    List<Product> findAllByPriceGreaterThan(Double price);
    List<Product> findAllByPriceLessThan(Double price);
    List<Product> findAllByPriceGreaterThanAndPriceLessThan(Double priceMin, Double priceMax);

    @Query(value = "SELECT MAX(d.price) FROM Product d")
    Double getMaxPrice();

    @Query(value = "SELECT MIN(d.price) FROM Product d")
    Double getMinPrice();


}
