package com.khai.admin.service;

import com.khai.admin.exception.StorageException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
public class FileService {

    private final ResourceLoader rsloader;
    @Value("${folder.absPath}")
    private String dataFolderPath;
    private final String TEST = "test";
    private final String PRODUCT = "product";
    private final String EMPLOYEE = "employee";

    @Autowired
    public FileService(ResourceLoader rsloader) {
        this.rsloader = rsloader;
    }

    @PostConstruct
    public void init() {
        try {
            Path testPath = Path.of(dataFolderPath + "/" + TEST);
            Path productPath = Path.of(dataFolderPath + "/" + PRODUCT);
            Path employeePath = Path.of(dataFolderPath + "/" + EMPLOYEE);
            Files.createDirectories(testPath);
            Files.createDirectories(productPath);
            Files.createDirectories(employeePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] getFileFromSystem(Path path) {

        try {
            byte[] file = Files.readAllBytes(path);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteFileFromSystem(Path path) {
        boolean isDeleted = false;
        try {
            Files.delete(path);
            isDeleted = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return isDeleted;
        }
    }
    public boolean deleteFileFromSystem(String path) {
        return this.deleteFileFromSystem(Path.of(path));
    }

    public void deleteFilesFromSystem(List<Path> paths) {
        try {
            paths.stream().forEach(path -> deleteFileFromSystem(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean uploadFileToSystem(MultipartFile file, String filePath) {

        boolean isSaveToFileSystem = false;
        try {
            File newfile = new File(filePath);
            if(file.isEmpty()) {
                throw new StorageException("File lỗi");
            }
            // save file to file system
            file.transferTo(newfile);
            isSaveToFileSystem = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        } catch( IllegalStateException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        } finally {
            return isSaveToFileSystem;
        }
    }

    /**
     * Phương thức upload nhiều file được tải lên từ client
     * @param files
     * @param folderPath
     * @return đường dẫn của file để chuyển thành api localhost://8080/api/files/<file-name>
     */
    public List<String> uploadMultipleFilesToSystem(MultipartFile[] files, String folderPath) {
        List<String> resultList = new ArrayList<>();
        try {
            if(files != null && files.length > 0) {
                Arrays.asList(files).stream().forEach(file -> {
                    String fileName = file.getOriginalFilename();
                    if(!isImageFile(fileName)) {
                        throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "File không phải định dạng hình ảnh");
                    }
                    int extensionIndex = fileName.lastIndexOf(".");
                    String extensionFile = fileName.substring(extensionIndex);
                    String name = fileName.substring(0, extensionIndex);
                    String filePath = folderPath + "/" + name + "_" + System.currentTimeMillis() + extensionFile;
                    boolean uploaded = this.uploadFileToSystem(file, filePath);
                    if(uploaded) {
                        resultList.add(filePath);
                    } else {
                        System.out.println("Tải files "+file.getOriginalFilename()+" lên không thành công");
                    }
                });
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Tải files lên không thành công");
        } finally {
            return resultList;
        }
    }

    public String uploadMultipleFile(MultipartFile file, String folderPath) {
        String result = null;
        if(file != null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            if(!isImageFile(filename)) throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "File không phải định dạng hình ảnh");
            String ext = filename.substring(filename.lastIndexOf("."));
            String path = folderPath + "/" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ext;
            if(uploadFileToSystem(file, path)) {
                result = path;
                System.out.println("Upload file: " + path);
            } else {
                System.out.println("Không thể tải file lên: " + path);
            }
        }
        return result;
    }

    public String getTestFolder() {
        return dataFolderPath + "/" + TEST;
    }

    public String getProductFolder() {
        return dataFolderPath + "/" + PRODUCT;
    }

    public String getEmployeeFolder() {
        return dataFolderPath + "/" + EMPLOYEE;
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
    public List<String> getImageUrls(String imagePaths) {
        List<String> imgUrls = new ArrayList<>();
        Arrays.asList(imagePaths.split(",")).forEach(image -> {
            try {
                Resource resources = rsloader.getResource(image);
                imgUrls.add(resources.getURL().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return imgUrls;
    }

    public List<String> getImageUrls(List<String> imagePath) {
        List<String> imgUrls = new ArrayList<>();
        imagePath.forEach(image -> {
            try {
                Resource resources = rsloader.getResource(image);
                imgUrls.add(resources.getURL().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return imgUrls;
    }

    public void updateFileInSystem(List<String> oldPath, List<String> newPath) {
        oldPath.forEach(path -> {
            if(!newPath.contains(path)) {
                this.deleteFileFromSystem(path);
            }
        });
    }

    public boolean isImageFile(String filename) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff", ".tif", ".svg", ".webp", "heif"};
        for (String extension : imageExtensions) {
            if (filename.endsWith(extension)) {
                return true;
            }
        }

        return false;

    }
}
