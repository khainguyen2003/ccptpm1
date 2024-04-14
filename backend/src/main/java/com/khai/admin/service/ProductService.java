package com.khai.admin.service;

import com.khai.admin.dto.Product.ProductBarcode;
import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.User;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.CategoryRepository;
import com.khai.admin.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
@Transactional
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;
    private UserService userService;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    private final int BATCH_SIZE = 10;

    @Autowired
    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        UserService userService,
        FileService fileService
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    public ProductDto getProductInfo(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return product.get().toDto();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có id" + id);
    }
//    public List<Product> getProducts(Pageable pageable) {
//
//    }

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

    @Transactional
    public ProductDto create(Map<String, String> headers, ProductDto updatedProduct) {
        try {
            Date now = new Date();
            if(productRepository.isExist(updatedProduct.getName(), updatedProduct.getCode()).isPresent()) {
                throw new AlreadyExist("Sản phẩm");
            }
            User user = userService.getUserById(1);
            Product newproduct = new Product();
            updatedProduct.applyToProduct(newproduct);

            newproduct.setDeleted(false);
            newproduct.setCreatedDate(now);
            if(updatedProduct.getCategory() != null) {
                Category category = categoryRepository.findById(updatedProduct.getCategory().getId()).orElse(null);
                if(category != null)
                    newproduct.setCategory(category);
            }
            newproduct.setCreator(user);
            String productFolder = fileService.getProductFolder();
            List<String> uploadedPath = fileService.uploadMultipleFilesToSystem(updatedProduct.getFiles(), productFolder);

            newproduct.setImages(uploadedPath);
            productRepository.save(newproduct);

            return new ProductDto(newproduct);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Map<String, Object> getProducts(
            Pageable pageable,
            String search,
            byte allowSale,
            String stockOutDate,
            String stockoutStartDate,
            String stockoutEndDate,
            byte onHandFilter,
            String onHandFilterStr,
            int[] brandIds,
            byte directSell,
            byte relateToChanel
    ) {
        try {
            List<Product> products = new ArrayList<>();
            List<ProductDto> productDtoList = new ArrayList<>();

            Page<Product> pageProducts = productRepository.findAll(pageable);


            products = pageProducts.getContent();
            products.forEach(product -> {
                ProductDto item = new ProductDto(product);
                productDtoList.add(item);
            });
            Map<String, Object> response = new HashMap<>();
            response.put("products", productDtoList);
            response.put("curPage", pageProducts.getNumber());
            response.put("totalPage", pageProducts.getTotalPages());
            response.put("totalElements", pageProducts.getTotalElements());
            response.put("pageSize", pageProducts.getSize());
            response.put("numberOfElements", pageProducts.getNumberOfElements());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        try {
            Optional<Product> productOpt = productRepository.findById(id);
            if(!productOpt.isPresent()) {
                throw new NoSuchElementException("Sản phẩm", "id", String.valueOf(id));
            } else {
                Product product = productOpt.get();
                if(product.isDeleted()) {
                    productRepository.deleteById(id);
                } else {
                    productRepository.updateDeletedById(id, true);
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    @Transactional
    public ProductDto updateProduct(UUID id, ProductDto updatedProduct) {
        try {

            Optional<Product> optProduct = productRepository.findById(id);
            if(optProduct.isPresent()) {
                Product product = optProduct.get();
                updatedProduct.applyToProduct(product);
                List<String> updateImages = updatedProduct.getImages();
                fileService.updateFileInSystem(product.getImages(), updateImages);
                String productFolder = fileService.getProductFolder();
                List<String> uploadedImgPath = fileService.uploadMultipleFilesToSystem(updatedProduct.getFiles(), productFolder);
                if(!uploadedImgPath.isEmpty()) {
                    updateImages.addAll(uploadedImgPath);
                    product.setImages(updateImages);
                }
                productRepository.save(product);
                return product.toDto();
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
            return updatedProduct.toDto();
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

    @Transactional
    public boolean batchInsertProducts(List<Product> data) {
        for (int i = 0; i < data.size(); i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }

            entityManager.persist(data.get(i));

        }
        entityManager.flush();
        entityManager.clear();
        return false;
    }


    public List<ProductBarcode> printBarcode(UUID[] ids) {
        try {
            List<ProductBarcode> data = this.productRepository.findByIdIn(ids);
            return data;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch(Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
