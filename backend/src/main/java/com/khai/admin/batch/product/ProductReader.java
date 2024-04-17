package com.khai.admin.batch.product;

import com.khai.admin.dto.Product.ProductDto;
import com.khai.admin.entity.Product;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductReader implements ItemReader<ProductDto> {
    private StepExecution stepExecution;
    private int currentRow = 0; // Biến để theo dõi dòng hiện tại
    private String[] columnNames; // Mảng chứa tên các cột của file import
    private String inputFileName;

    public ProductReader() {
    }

    public ProductReader(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    @Override
    @StepScope
    public ProductDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        // Cập nhật thông tin về dòng hiện tại và tên các cột vào ExecutionContext
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        executionContext.putInt("currentRow", currentRow);
        executionContext.putString("columnNames", String.join(",", columnNames));

        currentRow++; // Tăng biến đếm dòng hiện tại
        return null;
    }

}
