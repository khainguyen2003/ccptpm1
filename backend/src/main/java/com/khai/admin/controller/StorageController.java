package com.khai.admin.controller;

import com.khai.admin.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test/files")
public class StorageController {
    private final StorageService storageService;
    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getFileUploaded() {
        String testFolder = storageService.getTestFolder();
        Path testPath = Paths.get(testFolder);
        System.out.println("testPath: " + testPath);
        List<String> files = storageService.loadAll(testPath).map(
                        path -> MvcUriComponentsBuilder.fromMethodName(StorageController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        String testFolder = storageService.getTestFolder();
        Resource file = storageService.loadFileAsResource(testFolder + File.separator + filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/convert")
    public ResponseEntity<String> converImgToText(@RequestParam("files") MultipartFile file) {
        String filePath = storageService.getTestFolder() + File.separator + file.getOriginalFilename();
        storageService.uploadFileToSystem(file, filePath);
        String result = storageService.convertImageToText(filePath);

        return new ResponseEntity<>("kết quả: " + result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        List<String> fileNames = storageService.uploadMultipleFilesToSystem(files);

        return new ResponseEntity<>("upload thành công: " + fileNames, HttpStatus.OK);
    }



}
