package com.khai.admin.service;

import com.khai.admin.exception.StorageException;
import com.khai.admin.repository.jpa.ResourceRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileService {

    private final ResourceLoader rsloader;
    private final ResourceRepository resourceRepository;
    @Value("${folder.dataPath}")
    private String dataFolderPath;

    private final String apiUrl = "http://localhost:8080/api/files";
    private final String TEST = "test";
    private final String PRODUCT = "product";
    private final String[] EXCEL_EXTENSIONS = {".xlsx", ".xlsm", ".xlsb", ".xltx", ".xltm", ".xls", ".xlt", ".xml", ".xlam", ".xla", ".xlw", ".Xlr"};
    private final String CATEGORY_FOLDER = "category";

    @Autowired
    public FileService(
            ResourceLoader rsloader,
            ResourceRepository resourceRepository
    ) {
        this.rsloader = rsloader;
        this.resourceRepository = resourceRepository;
    }

    @PostConstruct
    public void init() {
        try {
            Path testPath = Path.of(dataFolderPath + "/" + TEST);
            Path productPath = Path.of(dataFolderPath + "/" + PRODUCT);
            Path categoryPath = Path.of(dataFolderPath + "/" + CATEGORY_FOLDER);
            Files.createDirectories(testPath);
            Files.createDirectories(categoryPath);
            Files.createDirectories(productPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTestFolder() {
        return dataFolderPath + "/" + TEST;
    }
    public String getProductFolder() {
        return dataFolderPath + "/" + PRODUCT;
    }
    public String getCategoryFolder() {
        return dataFolderPath + "/" + CATEGORY_FOLDER;
    }


    public byte[] getFileFromSystem(Path path) {

        try {
            byte[] file = Files.readAllBytes(path);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public com.khai.admin.entity.Resource getFileByAssetId(String assetId) {
        Optional<com.khai.admin.entity.Resource> fileOpt = this.resourceRepository.findByAssetId(assetId);
        if(fileOpt.isPresent()) {
            return fileOpt.get();
        } else {
            throw new NoSuchElementException();
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

    public String uploadFileToSystem(MultipartFile file, String folder) {

        boolean isSaveToFileSystem = false;
        String filepath = null;
        try {
            String fileName = file.getOriginalFilename();
            fileName = convertToUniqueFileName(fileName);
            Path path = Paths.get(folder, fileName);
            Files.createDirectories(path.getParent());
            if(file.isEmpty()) {
                throw new StorageException("File lỗi");
            }
            // save file to file system
            Files.write(path, file.getBytes());

            // Save to db
            Date now = new Date();
            String assetID = String.valueOf(System.currentTimeMillis());
            com.khai.admin.entity.Resource resource_entity = new com.khai.admin.entity.Resource();
            resource_entity.setFile_name(fileName);
            resource_entity.setResource_type(file.getContentType());
            resource_entity.setFolder(folder);
            resource_entity.setAsset_id(assetID);
            resource_entity.setCreate_at(now);
//            filepath = this.apiUrl + "/" + assetID;
            resource_entity.setPublic_id(assetID);
            resourceRepository.save(resource_entity);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        } catch( IllegalStateException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        } finally {
            return filepath;
        }
    }

    /**
     * Phương thức upload nhiều file được tải lên từ client
     * @param files
     * @param folder
     * @return đường dẫn của file để chuyển thành api localhost://8080/api/files/<file-name>
     */
    public List<String> uploadMultipleFilesToSystem(MultipartFile[] files, String folder) {
        List<String> resultList = new ArrayList<>();
        try {
            if(files != null && files.length > 0) {
                Arrays.asList(files).stream().forEach(file -> {
                    String fileName = file.getOriginalFilename();
                    if(!isImageFile(fileName)) {
                        throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "File không phải định dạng hình ảnh");
                    }
                    String filePath = this.uploadFileToSystem(file, folder);
                    if(filePath != null) {
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

    public String convertToUniqueFileName(String fileName) {
        int extensionIndex = fileName.lastIndexOf(".");
        String extensionFile = fileName.substring(extensionIndex);
        String name = fileName.substring(0, extensionIndex);
        return name + "_" + System.currentTimeMillis() + extensionFile;
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
        return loadFileAsResource(Paths.get(filePath).toUri());
    }
    public Resource loadFileAsResource(URI uri) {
        try {
            Resource resource = new UrlResource(uri);
            if (resource.exists())
                return resource;
            else
                throw new RuntimeException("File not found ");
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found ", e);
        }
    }
    public Resource loadFileAsResource(Path filePath) {
        return loadFileAsResource(filePath.toUri());
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
        if(oldPath != null && !oldPath.isEmpty() && newPath != null && !newPath.isEmpty()) {
            oldPath.forEach(path -> {
                if(!newPath.contains(path)) {
                    this.deleteFileFromSystem(path);
                }
            });
        }
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

    /**
     * Phương thức lấy danh sách tên cột từ file người dùng gửi
     * @param file
     * @return
     * @throws IOException
     */
    public String[] getColumnNames(MultipartFile file) throws IOException {
        String[] columnNames = null;
        if(isExcelFile(file.getOriginalFilename())) {
            columnNames = getColumnNamesFromExcel(file);
        }
        return columnNames;
    }

    /**
     * Phương thức lấy danh sách tên cột từ file người dùng gửi đã được upload tạm thời lên hệ thống để dùng cho batch config
     * @param path
     * @return
     * @throws IOException
     */
    public String[] getColumnNames(String path) {
        File file = new File(path);
        List<String> columnNames = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook;
            if(path.endsWith(".xlsx")) {
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
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return columnNames.toArray(new String[0]);
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

    public String[] getColumnNamesFromExcel(String path) throws IOException {
        List<String> columnNames = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(path);;
//        if(path.endsWith(".xlsx")) {
//            workbook = new XSSFWorkbook(path);
//        } else {
//            workbook = new HSSFWorkbook(path);
//        }


        Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet contains the data
        Row row = sheet.getRow(0); // Assuming the first row contains column names

        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columnNames.add(cell.getStringCellValue());
        }

        return columnNames.toArray(new String[0]);
    }

    public String[] getExcelExtensions() {
        return EXCEL_EXTENSIONS;
    }

    /**
     * Phương thức tìm vị trí của các header đã định nghĩa trong header sheet<br/>
     * Nếu tìm thấy thì thêm vào danh sách tên cột và vị trí cột<br/>
     * Nếu không tìm thấy thì cho vị trí là -1
     * @param sheet
     * @param headers Tên các trường đã định nghĩa của file
     * @return danh sách vị trí và tên cột
     * @Author Khải
     */
    public Map<String, Integer> findPosHeaders(Sheet sheet, String[] headers) {
        Map<String, Integer> headersPos = new HashMap<>();
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return null; // No header row found
        }

        int lastColumn = headerRow.getLastCellNum();
        for (int i = 0; i < headers.length; i++) {
            String requiredHeader = headers[i];
            boolean headerFound = false;
            for (int j = 0; j < lastColumn; j++) {
                Cell cell = headerRow.getCell(j);
                if (cell != null && cell.getStringCellValue().equals(requiredHeader)) {
                    headersPos.put(requiredHeader, j);
                    headerFound = true;
                    break;
                }

                headersPos.put(requiredHeader, -1);
            }
            if (!headerFound) {
                return null; // Required header not found
            }
        }
        return headersPos; // All required headers found
    }



}
