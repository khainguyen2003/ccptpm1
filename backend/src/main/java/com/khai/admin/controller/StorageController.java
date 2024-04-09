package com.khai.admin.controller;

import com.khai.admin.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test/files")
public class StorageController {
    private final FileService fileService;
    @Autowired
    public StorageController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getFileUploaded() {
        String testFolder = fileService.getTestFolder();
        Path testPath = Paths.get(testFolder);
        List<String> files = fileService.loadAll(testPath).map(
                        path -> MvcUriComponentsBuilder.fromMethodName(StorageController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        String testFolder = fileService.getTestFolder();
        Resource file = fileService.loadFileAsResource(testFolder + "/" + filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/convert")
    public ResponseEntity<String> converImgToText(@RequestParam("files") MultipartFile file) {
        String filePath = fileService.getTestFolder() + "/" + file.getOriginalFilename();
        fileService.uploadFileToSystem(file, filePath);
        String result = fileService.convertImageToText(filePath);

        return new ResponseEntity<>("kết quả: " + result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        String testFolder = fileService.getTestFolder();
        List<String> fileNames = fileService.uploadMultipleFilesToSystem(files, testFolder);

        return new ResponseEntity<>("upload thành công: " + fileNames, HttpStatus.OK);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        String testFolder = fileService.getTestFolder();
        Path filePath = Path.of(testFolder + "/" + filename);

        fileService.deleteFileFromSystem(filePath);
        return new ResponseEntity<>("Xóa file " + filename + " thành công", HttpStatus.OK);
    }



}
