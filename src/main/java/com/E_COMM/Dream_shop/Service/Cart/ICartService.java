package com.E_COMM.Dream_shop.Service.Cart;

import com.E_COMM.Dream_shop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


}
