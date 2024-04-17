package com.khai.admin.util;

import com.khai.admin.exception.StorageException;
import jakarta.annotation.PostConstruct;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class FileHelper {
    private final ResourceLoader rsloader;
    @Value("${folder.absPath}")
    private String dataFolderPath;
    private final String TEST = "test";
    private final String PRODUCT = "product";
    private final String[] EXCEL_EXTENSIONS = {".xlsx", ".xlsm", ".xlsb", ".xltx", ".xltm", ".xls", ".xlt", ".xml", ".xlam", ".xla", ".xlw", ".Xlr"};

    @Autowired
    public FileHelper(ResourceLoader rsloader) {
        this.rsloader = rsloader;
    }

    @PostConstruct
    public void init() {
        try {
            Path testPath = Path.of(dataFolderPath + "/" + TEST);
            Path productPath = Path.of(dataFolderPath + "/" + PRODUCT);
            Files.createDirectories(testPath);
            Files.createDirectories(productPath);
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

    public String getTestFolder() {
        return dataFolderPath + "/" + TEST;
    }
    public String getProductFolder() {
        return dataFolderPath + "/" + PRODUCT;
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

    public boolean isExcelFile(String filename) {
        for (String extension : EXCEL_EXTENSIONS) {
            if (filename.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    public String[] getColumnNames(MultipartFile file) throws IOException {
        String[] columnNames = null;
        if(isExcelFile(file.getOriginalFilename())) {
            columnNames = getColumnNamesFromExcel(file);
        }
        return columnNames;
    }

    public String[] getColumnNamesFromExcel(MultipartFile file) throws IOException {
        List<String> columnNames = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();) {
            Workbook workbook;
            if(file.getContentType().equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }

            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet contains the data
            Row row = sheet.getRow(0); // Assuming the first row contains column names

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                columnNames.add(cell.getStringCellValue());
            }
        }

        return columnNames.toArray(new String[0]);
    }

    public String[] getExcelExtensions() {
        return EXCEL_EXTENSIONS;
    }
}
