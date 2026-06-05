package com.E_COMM.Dream_shop.Service.Category;

import com.E_COMM.Dream_shop.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryByName(String name);
    Category getCategoryById(Long id);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategoryById(Long id);

}

