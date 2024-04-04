package com.khai.admin.service;

import com.khai.admin.exception.StorageException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class StorageService {

    @Value("${folder.absPath}")
    private String folderPath;
    private final String TEST = "test";

    public StorageService() {
    }

    @PostConstruct
    public void init() {
        try {
            Path testPath = Path.of(folderPath + File.separator + TEST);
            Files.createDirectories(testPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] getFileFromSystem(String fileName) {

        try {
            byte[] file = Files.readAllBytes(Path.of(folderPath + fileName));
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteFileFromFileSystem(String fileName) {
        boolean isDeleted = false;
        try {
            Files.delete(Path.of(folderPath + fileName));
            isDeleted = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return isDeleted;
        }
    }

    public boolean uploadFileToSystem(MultipartFile file, String filePath) {

        boolean isSaveToFileSystem = false;

        try {
            if(file.isEmpty()) {
                throw new StorageException("File lỗi");
            }
            // save file to file system
            file.transferTo(new File(filePath));
            isSaveToFileSystem = true;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            return isSaveToFileSystem;
        }
    }

    public List<String> uploadMultipleFilesToSystem(MultipartFile[] files) {
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                String filePath = this.getTestFolder() + File.separator + file.getOriginalFilename();
                this.uploadFileToSystem(file, filePath);
                fileNames.add(file.getOriginalFilename());
            });
            return fileNames;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Tải files không thành công");
        }
    }

    public String getTestFolder() {
        return folderPath + File.separator + TEST;
    }

    /**
     * Phương thức lấy đường dẫn của các file trong một folder chỉ định
     * @param folderPath Đường dẫn của folder cần lấy
     * @return
     */

    public Stream<Path> loadAll(Path folderPath) {
        try {

            return Files.walk(folderPath, 1)
                    .filter(path -> !path.equals(folderPath))
                    .map(folderPath::relativize);
        } catch(IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    public Resource loadFileAsResource(String filePath) {
        try {
            URI uri = Paths.get(filePath).toUri();
            Resource resource = new UrlResource(uri);
            if (resource.exists())
                return resource;
            else
                throw new RuntimeException("File not found " + filePath);
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + filePath, e);
        }
    }

    public String convertImageToText(String imagePath) {
        File image = new File(imagePath);
        Tesseract tesseract = new Tesseract();
        try {
            String extractedText = tesseract.doOCR(image);
            System.out.println("Extracted text: " + extractedText);
            return extractedText;
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return "";
    }
}
