package com.khai.admin.service;

import com.khai.admin.dto.category.CategoryViewDto;
import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.user.UserViewDto;
import com.khai.admin.entity.Category;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.User;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.CategoryRepository;
import com.khai.admin.repository.ProductRepository;
import com.khai.admin.repository.impl.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private UserService userService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        UserService userService
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public ProductDto getProductInfo(int id) {
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
    public ProductDto create(Map<String, String> headers, Product product) {
        try {
            Date now = new Date();
            if(productRepository.isExist(product.getName(), product.getCode()).isPresent()) {
                throw new AlreadyExist("Sản phẩm");
            }
            User user = userService.getUserById(1);

            product.setDeleted(false);
            product.setCreatedDate(now);
            Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
            if(category != null)
                product.setCategory(category);
            product.setCreator(user);
            productRepository.save(product);

            return product.toDto();
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
//            Page<Object[]> pageProducts = productRepository.getProducts(Specification.where(ProductSpecifications.hasSearch(search)), pageable);;
            /*for (Object[] objects : pageProducts.getContent()) {
                ProductDto p = new ProductDto((Product)objects[0]);
                UserViewDto u = new UserViewDto((Integer)objects[1], String.valueOf(objects[2]), String.valueOf(objects[3]), String.valueOf(objects[4]), String.valueOf(objects[5]));
                p.setCreatedBy(u);
                CategoryViewDto c = new CategoryViewDto((Integer)objects[6], String.valueOf(objects[7]));
                p.setCategory(c);
                productDtoList.add(p);
            }*/
            Specification<Product> searchSpec = ProductSpecifications.hasSearch(search);
            Specification<Product> selectSpec = ProductSpecifications.selectFields();
            // Kết hợp hai Specification thành một
            Specification<Product> combinedSpec = Specification.where(searchSpec).and(selectSpec);

            Page<Product> pageProducts = productRepository.findAll(
                    ProductSpecifications.selectFields()
                    .and(ProductSpecifications.hasSearch(search)),
                    pageable
            );

            products = pageProducts.getContent();
            products.stream().map(ProductDto::new).forEach(productDtoList::add);
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
    public void deleteById(int id) {
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
    public ProductDto updateProduct(int id, Product updatedProduct) {
        try {
            Optional<Product> optProduct = productRepository.findById(id);
            if(optProduct.isPresent()) {
                Product product = optProduct.get();
                updatedProduct.applyToProduct(product);
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
    public ProductDto updateSellStatus(int id, ProductDto productDto) {
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
}
