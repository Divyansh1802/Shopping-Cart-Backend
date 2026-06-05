package com.E_COMM.Dream_shop.Service.Order;

import com.E_COMM.Dream_shop.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
