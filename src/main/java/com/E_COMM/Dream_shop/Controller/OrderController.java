package com.E_COMM.Dream_shop.Controller;

import com.E_COMM.Dream_shop.Response.ApiResponse;
import com.E_COMM.Dream_shop.Service.Order.OrderService;
import com.E_COMM.Dream_shop.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/Orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/Place-order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam  Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok().body(new ApiResponse("Order created successfully", order));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable  Long orderId){
        try{
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok().body(new ApiResponse("Order found successfully", order));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable  Long userId){
        try{
            List<Order> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok().body(new ApiResponse("Order found successfully", orders));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
