package com.E_COMM.Dream_shop.Repository;

import com.E_COMM.Dream_shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);

    Category findById(Long id);

    void deleteById(Long id);

    boolean existsByName(String name);
}
