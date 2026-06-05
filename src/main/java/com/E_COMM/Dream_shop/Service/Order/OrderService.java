package com.E_COMM.Dream_shop.Service.Order;

import com.E_COMM.Dream_shop.Enums.OrderStatus;
import com.E_COMM.Dream_shop.Repository.OrderRepository;
import com.E_COMM.Dream_shop.Repository.ProductRepository;
import com.E_COMM.Dream_shop.Service.Cart.CartService;
import com.E_COMM.Dream_shop.exceptions.ResourceNotFoundException;
import com.E_COMM.Dream_shop.model.Cart;
import com.E_COMM.Dream_shop.model.Order;
import com.E_COMM.Dream_shop.model.OrderItem;
import com.E_COMM.Dream_shop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository  productRepository;
    private final CartService  cartService;


    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalPrice(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
           Product product = cartItem.getProduct();
           product.setInventory(product.getInventory() - cartItem.getQuantity());
           productRepository.save(product);
           return new OrderItem(
                   order,product,cartItem.getQuantity(),
                   cartItem.getUnitPrice()
           );
        }).toList();
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalAmount = totalAmount.add(orderItem.getPrice()
                    .multiply(new BigDecimal(orderItem.getQuantity())));
        }
        return totalAmount;
    }


    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("order not found")
        );
    }

    public List<Order> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);
    }
}
