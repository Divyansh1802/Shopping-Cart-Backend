package com.E_COMM.Dream_shop.Repository;

import com.E_COMM.Dream_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByCategoryNameAndName(String category, String name);

    Long countByBrandAndName(String brand, String name);

    Long countByBrand(String brand);

    Long countByCategoryName(String category);

    boolean existsByNameAndBrand(String name, String brand);
}
