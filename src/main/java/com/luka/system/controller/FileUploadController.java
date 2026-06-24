package com.luka.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final String UPLOAD_DIR =
            "uploads/invoices/";
    private final String UPLOAD_DIR_PRODUCT =
            "uploads/products/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            File dir = new File(UPLOAD_DIR);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path path = Paths.get(
                    UPLOAD_DIR + file.getOriginalFilename()
            );

            Files.write(path, file.getBytes());

            return ResponseEntity.ok(
                    "/uploads/invoices/" +
                            file.getOriginalFilename()
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Upload failed");
        }
    }
    @PostMapping("/upload/product")
    public ResponseEntity<String> uploadProductImage(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            File dir = new File(UPLOAD_DIR_PRODUCT);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path path = Paths.get(
                    UPLOAD_DIR_PRODUCT + file.getOriginalFilename()
            );

            Files.write(path, file.getBytes());

            return ResponseEntity.ok(
                    "/uploads/products/" +
                            file.getOriginalFilename()
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Upload failed");
        }
    }
}