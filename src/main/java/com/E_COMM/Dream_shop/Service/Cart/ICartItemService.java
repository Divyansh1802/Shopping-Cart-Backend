package com.E_COMM.Dream_shop.Service.Cart;

import com.E_COMM.Dream_shop.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateCartItemQuantity(Long cartId,Long productId,int quantity);

}
