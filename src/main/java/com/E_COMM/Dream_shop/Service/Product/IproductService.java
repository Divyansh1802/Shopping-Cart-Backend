package com.E_COMM.Dream_shop.Service.Product;

import com.E_COMM.Dream_shop.model.Product;
import com.E_COMM.Dream_shop.request.AddProductRequest;
import com.E_COMM.Dream_shop.request.UpdateProductRequest;

import java.util.List;

public interface IproductService {
    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest  request,Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    List<Product> getProductsByCategoryAndName(String category,String name);
    Long countAllProducts();
    Long countAllProductsByCategory(String category);
    Long countAllProductsByBrand(String brand);
}
