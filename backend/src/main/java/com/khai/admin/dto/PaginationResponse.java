package com.khai.admin.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class PaginationResponse {
    private List<?> items;
    private int curPage;
    private int totalPage;
    private long totalElements;
    private int pageSize;
    private int numberOfElements;


}
