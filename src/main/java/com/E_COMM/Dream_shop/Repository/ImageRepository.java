package com.E_COMM.Dream_shop.Repository;

import com.E_COMM.Dream_shop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
      Image findImageById(Long id);
}
