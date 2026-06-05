package com.E_COMM.Dream_shop.Controller;

import com.E_COMM.Dream_shop.Response.ApiResponse;
import com.E_COMM.Dream_shop.Service.Image.ImageService;
import com.E_COMM.Dream_shop.dto.ImageDto;
import com.E_COMM.Dream_shop.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final  ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productID) {
        try {
            List<ImageDto> imageDTO = imageService.saveImage(files, productID);
            return ResponseEntity.ok(new ApiResponse("Upload Success", imageDTO));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/image/download/{imageID}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageID) {
        try {
            Image image = imageService.getImageById(imageID);
            ByteArrayResource resource= new ByteArrayResource((image.getImage().getBytes(1,(int)image.getImage().length())));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + image.getFileName() + "\""
                    )
                    .contentLength(image.getImage().length())
                    .body(resource);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/image/{imageID}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageID, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageID);
            if(image != null) {
                imageService.updateImage(file, imageID);
                return ResponseEntity.ok(new ApiResponse("Update Success", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Error", null));
    }

    @DeleteMapping("/image/{imageID}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageID) {
        try {
            Image image = imageService.getImageById(imageID);
            if(image != null) {
                imageService.deleteImageById(imageID);
                return ResponseEntity.ok(new ApiResponse("Delete Success", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Error", null));
    }

}









