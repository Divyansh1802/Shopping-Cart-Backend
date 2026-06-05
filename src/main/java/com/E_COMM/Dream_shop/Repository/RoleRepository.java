package com.E_COMM.Dream_shop.Repository;

import com.E_COMM.Dream_shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}
