package com.khai.admin.service;

import com.khai.admin.constants.MessResConstants;
import com.khai.admin.dto.PaginationResponse;
import com.khai.admin.dto.Product.*;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.User;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.projections.ProductPreview;
import com.khai.admin.repository.impl.ProductSpecifications;
import com.khai.admin.repository.jpa.ProductRepository;
import com.khai.admin.util.Utilities_text;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ProductService {
    @Autowired
    protected ProductRepository productRepository;
    protected UserService userService;
    protected CategoryService categoryService;
    protected FileService fileService;
//    private NotificationService notificationService;

    private static Map<String, Class<? extends Product>> productRegistry = new HashMap<>();

    @Autowired
    public ProductService(
        ProductRepository productRepository,
        CategoryService categoryService,
        UserService userService,
        FileService fileService
    ) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.fileService = fileService;
//        this.notificationService = notificationService;
    }

    public static void registerProductType(String type, Class<? extends Product> classRef) {
        ProductService.productRegistry.put(type, classRef);
    }

    public Product getProductById(UUID id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if(productOpt.isPresent()) {
            Product p = productOpt.get();
            return p;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có id" + id);
    }

    public ProductDetail getProductInfo(UUID id) {
        Product p = getProductById(id);
        ProductDetail response = new ProductDetail();
        response.fromEntity(p);
        return response;
    }

    @Transactional
    public void importData(List<Product> data) {
        try {
            for (Product item : data) {
                System.out.println(item);
//                productRepository.save(item);
            }
        }catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Phương thức tạo sản phẩm dựa vào dữ liệu người dùng đưa vào theo form-data và ánh xạ thành ProductCreateRequest
     * Các bước thực hiện
     *  1. Product Có giá trị thuộc tính name và name này chưa tồn tại
     *  2. User đã đăng nhập và Thuộc tính shop sẽ là user đang thực hiện request
     *  3. Nếu có type trong request thì sẽ kiểm tra có tồn tại tên type đó chưa
     *  4. Các ảnh chưa được người dùng xóa sẽ được đưa vào thuộc tính oldImages. Hệ thống sẽ kiểm tra trường xem đường dẫn
     *      nào không tồn tại trong trường oldImages thì sẽ xóa khỏi hệ thống
     *  5. Các ảnh mới sẽ lưu trong new_images. Hệ thống sẽ lưu các ảnh mới vào hệ thống và chuyển thành các đường dẫn
     *  6. Dựa theo type của Product thì hệ thống sẽ tạo ra các đối tượng tương ứng với các trường là giá trị của attrs
     *  7. Ánh xạ thành Product, Lưu vào database và trả về response
     * @param headers
     * @param createRequest
     * @return
     */
    @Transactional
    public ProductDto create(Map<String, String> headers, ProductSaveRequest createRequest) {
        if(!StringUtils.hasText(createRequest.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên sản phẩm không được bỏ trống");
        }
        Date now = new Date();
        if(productRepository.isExist(createRequest.getName(), createRequest.getCode()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sản phẩm đã tồn tại");
        }
        if(createRequest.getImportPrice() >= createRequest.getSellPrice()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Giá bán phải lớn hơn giá nhập");
        }

        User user = userService.getCurrentLogin();
        Product newproduct = new Product();
        createRequest.applyToProduct(newproduct);

        newproduct.setDeleted(false);
        newproduct.setCreatedDate(now);
        newproduct.setLastmodified(now);
        if(createRequest.getCategoryId() != null && !createRequest.getCategoryId().equalsIgnoreCase("undefined") && !createRequest.getCategoryId().equalsIgnoreCase("null")) {
            UUID categoryId = UUID.fromString(createRequest.getCategoryId());
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                newproduct.setCategory(category);
            }
        }
        if(user != null) {
            newproduct.setShop(user);
            newproduct.setLastModifiedBy(user);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vui lòng đăng nhập");
        }

        // upload new product images
        String productFolder = fileService.getProductFolder();
        System.out.println(createRequest.getNew_images());
//            if(createRequest.getNew_images() != null) {
//                List<String> uploadedPath = fileService.uploadMultipleFilesToSystem(createRequest.getNew_images(), productFolder);
//                newproduct.setImages(uploadedPath);
//            }
        System.out.println("thumb: " + createRequest.getThumb());
        if(createRequest.getThumb() != null) {
            System.out.println("abbc");
            String thumbPath = fileService.uploadFileToSystem(createRequest.getThumb(), productFolder);
            if(thumbPath != null) {
                newproduct.setProduct_thumb(thumbPath);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tải file lên không thành công");
            }
        }
        // upload load product thumbnail

        // save product attributes
        newproduct = productRepository.save(newproduct);

        // push to notification system
//            Map<String, Object> more_details = new HashMap<>();
//            notificationService.pushNotiToSystem(NOTIFICATION_TYPE.SHOP_001, user.getId(), "1", more_details, null);

        return new ProductDto(newproduct);
    }

    public PaginationResponse getProducts(
            Pageable pageable,
            String search
    ) {
        try {
            List<Product> products;
            List<ProductDto> productDtoList = new ArrayList<>();
            Page<Product> pageProducts;
            pageProducts = productRepository.findAll(pageable);

            products = pageProducts.getContent();
            products.forEach(product -> {
                ProductDto item = new ProductDto(product);
                productDtoList.add(item);
            });
            PaginationResponse response = PaginationResponse.builder()
                    .items(productDtoList)
                    .curPage(pageProducts.getNumber())
                    .pageSize(pageProducts.getSize())
                    .totalPage(pageProducts.getTotalPages())
                    .totalElements(pageProducts.getTotalElements())
                    .build();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public List<Product> findAllByIdIn(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids);
    }

    public Page<ProductPreview> getProductByShopId(UUID shopId, Pageable pageable) {
        Page<ProductPreview> products = productRepository.findByShopId(shopId, pageable);
        return products;
    }

    @Transactional
    public boolean deleteById(UUID id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Transactional
    public ProductDto updateProduct(UUID id, ProductSaveRequest saveRequest) {
        try {
            if(!StringUtils.hasText(saveRequest.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên sản phẩm không được bỏ trống");
            }
            if(saveRequest.getImportPrice() >= saveRequest.getSellPrice()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Giá bán phải lớn hơn giá nhập");
            }
            Optional<Product> optProduct = productRepository.findById(id);
            if(optProduct.isPresent()) {
                Product product = optProduct.get();
                saveRequest.applyToProduct(product);
                if(saveRequest.getCategoryId() != null && !saveRequest.getCategoryId().equalsIgnoreCase("undefined") && !saveRequest.getCategoryId().equalsIgnoreCase("null")) {
                    UUID categoryId = UUID.fromString(saveRequest.getCategoryId());
                    Category category = categoryService.getCategoryById(categoryId);
                    if (category != null) {
                        product.setCategory(category);
                    }
                }
//                List<String> updateImages = saveRequest.getOld_images();
//                fileService.updateFileInSystem(product.getImages(), updateImages);
//                String productFolder = fileService.getProductFolder();
//                List<String> uploadedImgPath = fileService.uploadMultipleFilesToSystem(saveRequest.getNew_images(), productFolder);
//                if(!uploadedImgPath.isEmpty()) {
//                    updateImages.addAll(uploadedImgPath);
//                    product.setImages(updateImages);
//                }
                productRepository.save(product);
                return new ProductDto(product);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    public ProductDto updateSellStatus(UUID id, ProductDto productDto) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if(!product.isPresent()) {
                throw new NoSuchElementException("Sản phẩm", "id", String.valueOf(id));
            }
            Product updatedProduct = product.get();
            updatedProduct.setStopCell(productDto.isStopCell());
            productRepository.save(updatedProduct);
            return new ProductDto(updatedProduct);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public boolean bulkUpdateSellStatus(UUID[] ids, boolean sellStatus) {
        boolean success = false;
        try {
            productRepository.updateSellStatusByIdIn(ids, sellStatus);
            success = true;
        } catch (DataAccessException e) {
            success = false;
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            success = false;
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            return success;
        }
    }

    /** phương thức xử lý nhập dữ liệu bằng file excel
     * 1 - kiểm tra có phải file excel không (.xls hoặc xlsx)<br/>
     * 2 - Kiểm tra có các trường yêu cầu không. Nếu không đủ cột yêu cầu thì đưa ra danh sách cột thiếu<br/>
     * 3 - Lấy vị trí của các trường<br/>
     * 4 - Kiểm tra mã hàng đã tồn tại chưa<br/>
     * 5 - Nếu trùng mã hàng thì cập nhật thuộc tính mà người dùng đã chọn <br/>
     *      * Nếu Người dùng chọn trùng mã khác tên thì cập nhật tên -> cập nhat tên<br/>
     *      * Nếu Người dùng chọn trùng mã khác tên thì cập nhật tồn kho -> cập nhat tồn kho<br/>
     *      * Nếu Người dùng chọn trùng mã khác tên thì cập nhật giá vốn -> cập nhat giá vốn<br/>
     *      * Nếu Người dùng chọn trùng mã khác tên thì cập nhật mô tả -> cập nhat mô tả<br/>
     *
     * @param file chứa dữ liệu data có các cột yêu cầu
     * @return danh sách thông báo sau khi nhập<br/>
     *      Thông báo thành công nếu tất cả đều import thành công<br/>
     *      Thông báo thất bại và đòng lỗi và chi tiết lỗi nếu thất bại<br/>
     * @Author Khải
     */
    @Transactional
    public List<String> importExcelProducts(MultipartFile file) {
        List<Product> data = new ArrayList<>();
        List<String> errorMessList = new ArrayList<>();

        try {
            // Create Workbook instance holding reference to
            Workbook workbook;
            if (file.getContentType().equals(".xls")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }else if (file.getContentType().equals(".xls")) {// .xlsx file
                workbook = new HSSFWorkbook(file.getInputStream());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không phải file excel. Vui lòng chuyển sang định dạng .xlsx hoặc .xls");
            }
            // Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> headers = getProductHeaderPos(sheet);
            // 3 - validate header
            List<String> headersErr = validateHeader(headers);
//            if(headersErr != null && !headersErr.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, )
//                return errorMessList;
//            }
            // Xóa header của sheet để duyệt nội dung
            sheet.removeRow(sheet.getRow(0));

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            // Till there is an element condition holds true
            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                if(StringUtils.hasText(row.getCell(0).getStringCellValue())) {
                    int cnt = 0;
                    UUID prodID = UUID.fromString(row.getCell(0).getStringCellValue());
                    Optional<Product> productOpt = productRepository.findById(prodID);
                    Product p;
//                    String[] productHeader = {"Mã hàng", "Tên hàng", "Loại hàng", "Mã vạch", "Thương hiệu", "Giá bán", "Giá vốn", "Tồn kho", "Đơn vị tính", "Thuộc tính", "Trọng lượng", "Hình ảnh", "Đang kinh doanh", "Được bán trực tiếp", "Mô tả", "Mẫu ghi chú", "Vị trí"};
                    if(productOpt.isPresent()) {
                        p = productOpt.get();
                    } else {
                        p = new Product();
                        p.setId(prodID);
                    }
                    p.setName(row.getCell(1).getStringCellValue());
                    String catename = row.getCell(2).getStringCellValue();
                    if(StringUtils.hasText(catename)) {
                        Category category = categoryService.getCategoryByName(catename);
                        p.setCategory(category);
                    }
                    p.setCode(row.getCell(3).getStringCellValue());
                    p.setSell_price(row.getCell(4).getNumericCellValue());
                    p.setImport_price(row.getCell(5).getNumericCellValue());
                    p.setWeight(row.getCell(5).getStringCellValue());
                    p.setImages(Arrays.asList(row.getCell(6).getStringCellValue().split(",")));
                    p.setStopCell(!Utilities_text.isTrue(row.getCell(7).getStringCellValue()));
                    p.setDirectCell(Utilities_text.isTrue(row.getCell(8).getStringCellValue()));
                    p.setDescription(row.getCell(9).getStringCellValue());

                    data.add(p);
                }
            }
            // Closing file output streams
            workbook.close();

        }catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            return errorMessList;
        }
    }


    public Page<ProductPreview> printBarcode(List<UUID> ids, Pageable pageable) {
        try {
            Page<ProductPreview> data = this.productRepository.findByIdIn(ids, pageable);
            return data;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Map<String, Integer> getProductHeaderPos(Sheet sheet) {
        String[] productHeader = {"Mã hàng", "Tên hàng", "Loại hàng", "Mã vạch", "Thương hiệu", "Giá bán", "Giá vốn", "Tồn kho", "Đơn vị tính", "Thuộc tính", "Trọng lượng", "Hình ảnh", "Đang kinh doanh", "Được bán trực tiếp", "Mô tả", "Mẫu ghi chú", "Vị trí"};
        return fileService.findPosHeaders(sheet, productHeader);
    }

    public List<String> validateHeader(Map<String, Integer> requiredHeader) {
        List<String> errMess = new ArrayList<>();
        for (String key : requiredHeader.keySet()) {
            if(requiredHeader.get(key) == -1) {
                errMess.add("Thiếu cột " + key);
            }
        }
        return errMess;
    }

    /**
     * Thông tin của product là bản nháp khi trường isDraft của product là true
     * @return danh sách các product đang là bản nháp của shop có id chỉ định
     */
    public Map<String, Object> getAllProductDraft(UUID shopId, Pageable pageable) {
        Page<Product> productDraftPage = productRepository.findAllProductDraft(true, pageable);
        List<ProductDetail> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        if(!productDraftPage.isEmpty()) {
            result = productDraftPage.stream().map(item -> {
                ProductDetail p = new ProductDetail();
                p.fromEntity(item);
               return p;
            }).collect(Collectors.toList());

        }
        response.put("products", result);
        response.put("curPage", productDraftPage.getNumber());
        response.put("totalPage", productDraftPage.getTotalPages());
        response.put("totalElements", productDraftPage.getTotalElements());
        response.put("pageSize", productDraftPage.getSize());
        response.put("numberOfElements", productDraftPage.getNumberOfElements());

        return response;
    }

    public boolean publishProduct(UUID productId) {
        UUID user_update_id = userService.getCurrentLogin().getId();
        int rowAffected = productRepository.updatePublishStatus(user_update_id, productId, true, false);
        if(rowAffected != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy id sản phẩm");
        }
        return true;
    }

    public boolean unpublishProduct(UUID productId) {
        UUID user_update_id = userService.getCurrentLogin().getId();
        int rowAffected = productRepository.updatePublishStatus(user_update_id, productId, false, true);
        if(rowAffected != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy id sản phẩm");
        }
        return true;
    }
}
