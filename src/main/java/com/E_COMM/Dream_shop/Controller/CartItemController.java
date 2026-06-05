package com.E_COMM.Dream_shop.Controller;

import com.E_COMM.Dream_shop.Response.ApiResponse;
import com.E_COMM.Dream_shop.Service.Cart.CartItemService;
import com.E_COMM.Dream_shop.Service.Cart.CartService;
import com.E_COMM.Dream_shop.model.Cart;
import com.E_COMM.Dream_shop.model.User;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/Cart-Item")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            User user = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
            Cart cart = cartService.initializeCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("added item ", null));
        }
        catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("error ", null));
        }
        catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error",e.getMessage()));
        }

    }

    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("removed item ", null));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error",e.getMessage()));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId,
                                                      @PathVariable Long itemId,
                                                      @RequestParam Integer quantity) {
        try{
            cartItemService.updateCartItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("updated item ", null));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error",e.getMessage()));
        }
    }

}
