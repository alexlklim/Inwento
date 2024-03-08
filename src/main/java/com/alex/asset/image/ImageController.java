package com.alex.asset.image;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/image")
@Tag(name = "Image Controller", description = "Image API")
public class ImageController {

    private static final String UPLOAD_DIR = "src/main/resources/images";
    private static final String IMAGE_FILE_NAME = "uploaded_image.jpg";

    @Operation(summary = "Upload new image")
    @PostMapping
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        try {
            File existingImage = new File(UPLOAD_DIR, IMAGE_FILE_NAME);
            if (existingImage.exists()) {
                existingImage.delete();
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(IMAGE_FILE_NAME);
            Files.copy(file.getInputStream(), filePath);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Get image")
    @GetMapping
    public ResponseEntity<Resource> getImage() {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR).resolve(IMAGE_FILE_NAME);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
