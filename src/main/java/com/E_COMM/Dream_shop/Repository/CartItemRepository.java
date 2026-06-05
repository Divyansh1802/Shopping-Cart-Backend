package com.E_COMM.Dream_shop.Repository;

import com.E_COMM.Dream_shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    void deleteAllByCartId(Long id);
}
