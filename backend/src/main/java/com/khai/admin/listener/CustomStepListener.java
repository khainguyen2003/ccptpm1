package com.khai.admin.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

public class CustomStepListener {
    private StepExecution stepExecution;
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        // Thực hiện các hành động trước khi bước được thực thi
        // Lấy thông tin về dòng hiện tại (được đọc) từ ExecutionContext
        int currentRow = stepExecution.getExecutionContext().getInt("currentRow", 0);
        // Lấy thông tin về tên các cột từ ExecutionContext
        String[] columnNames = stepExecution.getExecutionContext().getString("columnNames").split(",");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        // Thực hiện các hành động sau khi bước được thực thi
    }

    // Các phương thức khác để thực hiện các hành động cụ thể nếu cần

}
