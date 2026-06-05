package com.E_COMM.Dream_shop.Service.Cart;

import com.E_COMM.Dream_shop.Repository.CartItemRepository;
import com.E_COMM.Dream_shop.Repository.CartRepository;
import com.E_COMM.Dream_shop.Service.Product.ProductService;
import com.E_COMM.Dream_shop.exceptions.ResourceNotFoundException;
import com.E_COMM.Dream_shop.model.Cart;
import com.E_COMM.Dream_shop.model.CartItem;
import com.E_COMM.Dream_shop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository  cartRepository;
    private final ProductService  productService;
    private final CartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart; // 2. Get the product ;
        // 3. check if product already in the cart , if yes , incr. qty. with the requested qty.
        // if no, then initiate new cartItem in the cart
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().
                        equals(product.getId())).findFirst().orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setQuantity(quantity);
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        CartItem itemToRemove = cart.getItems().stream()
                .filter(i -> i.getProduct().getId()
                        .equals(productId)).findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("not found"));
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCartById(cartId);
        cart.getItems().stream().filter(i -> i.getProduct()
                .getId().equals(productId))
                .findFirst().ifPresent(i -> {
                    i.setQuantity(quantity);
                    i.setUnitPrice(i.getProduct().getPrice());
                    i.setTotalPrice();
                });
        BigDecimal newTotalPrice = cart.getItems()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(newTotalPrice);
        cartRepository.save(cart);
    }
}
