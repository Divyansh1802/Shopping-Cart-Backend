package com.E_COMM.Dream_shop.Service.Product;

import com.E_COMM.Dream_shop.Repository.CategoryRepository;
import com.E_COMM.Dream_shop.Repository.ProductRepository;
import com.E_COMM.Dream_shop.exceptions.AlreadyExistsException;
import com.E_COMM.Dream_shop.exceptions.ProductNotFoundException;
import com.E_COMM.Dream_shop.model.Category;
import com.E_COMM.Dream_shop.model.Product;
import com.E_COMM.Dream_shop.request.AddProductRequest;
import com.E_COMM.Dream_shop.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements  IproductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
         // check if category is found in DB, if Yes, set it as the new Product Category, if no, then save it as new Category

        if(ProductExists(request)){
            throw new AlreadyExistsException("product already exists"+request.getName()+"with brand "+request.getBrand());
        }
        Category category = Optional.ofNullable(
                categoryRepository.findByName(request.getCategory().getName())).orElseGet(
                () -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                }
        );
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private boolean ProductExists(AddProductRequest request){
        return productRepository.existsByNameAndBrand(request.getName(),request.getBrand());
    }

    private Product createProduct(AddProductRequest request,Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getInventory(),
                request.getPrice(),
                category
        );
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
         productRepository.findById(id).ifPresentOrElse
                 (productRepository::delete,() ->
                 {throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest request,Long id) {
           return productRepository.findById(id).map(product -> updateExistingProduct(request,product))
                   .orElseThrow(() -> new ProductNotFoundException("Product not found")
           );
    }

    private Product updateExistingProduct(UpdateProductRequest request, Product product) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setInventory(request.getInventory());
        product.setPrice(request.getPrice());

        Category category = Optional.ofNullable(
                categoryRepository.findByName(request.getCategory().getName())).orElseGet(
                () -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                }
        );
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public List<Product> getProductsByCategoryAndName(String category, String name) {
        return productRepository.findByCategoryNameAndName(category,name);
    }

    @Override
    public Long countAllProducts() {
        return productRepository.count();
    }

    @Override
    public Long countAllProductsByCategory(String category) {
        return productRepository.countByCategoryName(category);
    }

    @Override
    public Long countAllProductsByBrand(String brand) {
        return productRepository.countByBrand(brand);
    }

}
