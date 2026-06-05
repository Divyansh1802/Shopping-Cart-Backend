package com.E_COMM.Dream_shop.Service.Image;

import com.E_COMM.Dream_shop.dto.ImageDto;
import com.E_COMM.Dream_shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long product_id);
    Image updateImage(MultipartFile file,Long image_id);
}
