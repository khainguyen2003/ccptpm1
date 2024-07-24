package com.khai.admin.service;

import com.khai.admin.dto.PaginationResponse;
import com.khai.admin.request.FilterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface CategoryDynamicServices {
    public PaginationResponse findByCriteria(FilterRequest filterRequest, Pageable pageable);
    public PaginationResponse findByProcedure(FilterRequest filterRequest, Pageable pageable);
    public PaginationResponse findByEntityManager(FilterRequest filterRequest, int pageNum, int pageSize, List<Sort.Order> orders);

    public boolean deleteById(UUID id);
}
