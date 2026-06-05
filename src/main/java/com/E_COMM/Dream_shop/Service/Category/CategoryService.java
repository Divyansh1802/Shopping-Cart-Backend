package com.E_COMM.Dream_shop.Service.Category;

import com.E_COMM.Dream_shop.Repository.CategoryRepository;
import com.E_COMM.Dream_shop.exceptions.AlreadyExistsException;
import com.E_COMM.Dream_shop.exceptions.ResourceNotFoundException;
import com.E_COMM.Dream_shop.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id);
        if(category == null){
            throw new ResourceNotFoundException("not found");
        }
        return category;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        if(!categoryRepository.existsByName(category.getName())){
           return  categoryRepository.save(category);
        }
        else{
            throw new AlreadyExistsException(category.getName());
        }
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(categoryRepository.findById(id)).map(oldcategory ->{
            oldcategory.setName(category.getName());
            return categoryRepository.save(oldcategory);
        }).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
