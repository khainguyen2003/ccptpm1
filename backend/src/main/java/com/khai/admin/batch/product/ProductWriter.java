package com.khai.admin.batch.product;

import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.entity.Product;
import com.khai.admin.repository.ProductRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductWriter implements ItemWriter<ProductDto> {
    @Autowired
    private ProductRepository productRepository;

    private boolean updateMode; // Chế độ cập nhật mặc định
    private boolean updateName;
    private boolean updateInventory;
    private boolean updateImportPrice;
    private boolean updateDesc;
    private List<String> errorMessages = new ArrayList<>();

    public ProductWriter() {
        updateMode = false;
        updateDesc = false;
        updateInventory = false;
        updateImportPrice = false;
    }

    public ProductWriter(boolean updateMode, boolean updateName, boolean updateInventory, boolean updateImportPrice, boolean updateDesc) {
        this.updateMode = updateMode;
        this.updateName = updateName;
        this.updateInventory = updateInventory;
        this.updateImportPrice = updateImportPrice;
        this.updateDesc = updateDesc;
    }

    @Override
    public void write(Chunk<? extends ProductDto> products) throws Exception {
        for (ProductDto product : products) {
            // Kiểm tra xem sản phẩm đã tồn tại trong cơ sở dữ liệu hay chưa
            Optional<Product> optProduct = productRepository.findById(product.getId());
            if (optProduct.isPresent()) {
                // Xử lý trường hợp trùng mã sản phẩm ở đây
                // Ví dụ: Nếu chế độ cập nhật được chọn, cập nhật thông tin sản phẩm
                if(updateMode) {
                    Product p = optProduct.get();
                    product.applyToProduct(p);
                    productRepository.save(p);
                } else { // Nếu không, thông báo lỗi hoặc bỏ qua sản phẩm
                    if(!updateName) {
                        errorMessages.add("- Trùng mã hàng khác tên hàng: ");
                    }
                }

            } else {
                // Nếu sản phẩm chưa tồn tại, thêm vào cơ sở dữ liệu
                Product p = new Product();
                product.applyToProduct(p);
                productRepository.save(p);
            }
        }
    }

    public boolean isUpdateMode() {
        return updateMode;
    }

    public void setUpdateMode(boolean updateMode) {
        this.updateMode = updateMode;
    }

    public boolean isUpdateName() {
        return updateName;
    }

    public void setUpdateName(boolean updateName) {
        this.updateName = updateName;
    }

    public boolean isUpdateInventory() {
        return updateInventory;
    }

    public void setUpdateInventory(boolean updateInventory) {
        this.updateInventory = updateInventory;
    }

    public boolean isUpdateImportPrice() {
        return updateImportPrice;
    }

    public void setUpdateImportPrice(boolean updateImportPrice) {
        this.updateImportPrice = updateImportPrice;
    }

    public boolean isUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(boolean updateDesc) {
        this.updateDesc = updateDesc;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
