package com.E_COMM.Dream_shop.Service.Image;

import com.E_COMM.Dream_shop.Repository.ImageRepository;
import com.E_COMM.Dream_shop.Service.Product.ProductService;
import com.E_COMM.Dream_shop.dto.ImageDto;
import com.E_COMM.Dream_shop.exceptions.ResourceNotFoundException;
import com.E_COMM.Dream_shop.model.Image;
import com.E_COMM.Dream_shop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService  productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        Image image = imageRepository.findImageById(id);
        if(image != null){
            imageRepository.delete(image);
        }
        else{
            throw new ResourceNotFoundException("Image not found");
        }
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long product_id) {
        Product product = productService.getProductById(product_id);
        List<ImageDto> IMAGEdto = new ArrayList<>();
        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = "/api/v1/images/image/downlaod/"+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl("/api/v1/images/image/downlaod/"+savedImage.getId());

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                IMAGEdto.add(imageDto);

            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return IMAGEdto;
    }

    @Override
    public Image updateImage(MultipartFile file, Long image_id) {
        Image image = getImageById(image_id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            return  imageRepository.save(image);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
