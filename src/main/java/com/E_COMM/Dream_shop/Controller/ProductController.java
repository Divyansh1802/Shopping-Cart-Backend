package com.E_COMM.Dream_shop.Controller;

import com.E_COMM.Dream_shop.Response.ApiResponse;
import com.E_COMM.Dream_shop.Service.Product.ProductService;
import com.E_COMM.Dream_shop.model.Product;
import com.E_COMM.Dream_shop.request.AddProductRequest;
import com.E_COMM.Dream_shop.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try{
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("success", products));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/id=/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try{
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("success", product));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/name=/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> product = productService.getProductsByName(name);
            return ResponseEntity.ok(new ApiResponse("success", product));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/name=/{name}/brand=/{brand}")
    public ResponseEntity<ApiResponse> getProductByNameAndBrand(@PathVariable String name, @PathVariable String brand){
        try{
            List<Product> products = productService.getProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("success", products));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/brand=/{brand}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand){
        try{
            List<Product> products = productService.getProductsByBrand(brand);
            return ResponseEntity.ok(new ApiResponse("success", products));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/category=/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        try{
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("success", products));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){
        try{
            Product product = productService.addProduct(request);
            return ResponseEntity.ok(new ApiResponse("add product success", product));
        }catch(Exception e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/id=/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request,@PathVariable Long id){
        try{
            Product product = productService.updateProduct(request,id);
            return ResponseEntity.ok(new ApiResponse("update product success", product));
        }
        catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("id=/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("delete product success", null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/category=/{category}/name=/{name}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndName(@PathVariable String category,
                                                                                  @PathVariable  String name){
        try{
            List<Product> products = productService.getProductsByCategoryAndName(category,name);
            return ResponseEntity.ok(new ApiResponse("success", products));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/category=/{category}/brand=/{brand}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(String category,String brand){
        try{
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
            return ResponseEntity.ok(new ApiResponse("success", products));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/countAll")
    public ResponseEntity<ApiResponse> CountProducts(){
        try{
            Long count = productService.countAllProducts();
            return ResponseEntity.ok(new ApiResponse("success", count));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/category=/{category}/count")
    public ResponseEntity<ApiResponse> CountProductsByCategory(@PathVariable String category){
        try{
            Long count = productService.countAllProductsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("success", count));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/brand=/{brand}/count")
    public ResponseEntity<ApiResponse> CountProductsByBrand(@PathVariable String brand){
        try{
            Long count = productService.countAllProductsByBrand(brand);
            return ResponseEntity.ok(new ApiResponse("success", count));
        }
        catch(Exception e){
            return ResponseEntity.ok(new ApiResponse("error", e.getMessage()));
        }
    }


}
