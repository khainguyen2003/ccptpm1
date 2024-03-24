package com.khai.admin.controller;

import com.khai.admin.entity.Log;
import com.khai.admin.service.HttpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {
    private final HttpServices httpServices;

    @Autowired
    public LogController(HttpServices httpServices) {
        this.httpServices = httpServices;
    }

    @GetMapping
    public List<Log> getLogs(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(defaultValue = "created_time:desc") String sort
    ) {
        List<Sort.Order> orders = this.httpServices.getSortOrders(sort);
        if(page < 1)
            page = 1;
        if(size < 0)
            size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));


        return null;
    }
}
