package com.E_COMM.Dream_shop.Service.Cart;

import com.E_COMM.Dream_shop.Repository.CartItemRepository;
import com.E_COMM.Dream_shop.Repository.CartRepository;
import com.E_COMM.Dream_shop.exceptions.ResourceNotFoundException;
import com.E_COMM.Dream_shop.model.Cart;
import com.E_COMM.Dream_shop.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException("Cart Not found with id " + id)
        );
        BigDecimal totalPrice = cart.getTotalAmount();
        cart.setTotalAmount(totalPrice);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = this.getCartById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart  cart = this.getCartById(id);
        return cart.getTotalAmount();
    }

    public Cart initializeCart(User user) {
        Cart cart = new Cart();
        return  cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
       return cartRepository.findByUserId(userId);
    }
}
