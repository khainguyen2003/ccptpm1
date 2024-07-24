package com.khai.admin.request;

import com.khai.admin.util.DateService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public record FilterRequest(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String fromDate,
        @RequestParam(required = false) String toDate
) {
//    public String keyword() {
//        return this.keyword == null ? "" : this.keyword;
//    }
//
//    public String notes() {
//        return this.keyword == null ? "" : this.notes;
//    }
}
