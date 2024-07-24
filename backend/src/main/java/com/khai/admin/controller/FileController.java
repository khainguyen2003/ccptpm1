package com.khai.admin.controller;

import com.khai.admin.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/api/files")
public class FileController {
    private FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{asset_id}")
    public ResponseEntity<Resource> serveFile(@PathVariable String asset_id) {
        com.khai.admin.entity.Resource resource_entity = this.fileService.getFileByAssetId(asset_id);
        String productFolder = resource_entity.getFolder();
        Path filePath = Paths.get(productFolder).resolve(resource_entity.getFile_name()).normalize();
        Resource resource = fileService.loadFileAsResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String testFolder = this.fileService.getTestFolder();
        String path = this.fileService.uploadFileToSystem(file, testFolder);
        return ResponseEntity.ok(path);
    }
}
