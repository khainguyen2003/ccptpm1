package com.khai.admin.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EmployeeDto {

    private int id;
    private Date contract_expire;
    private byte status;
    private Date start_date;
    private Date created_date;
    private Date modified_date;
    private boolean deleted;
    private String fullname;
    private Date birthday;
    private String email;
    private String phoneNumber;
    private String address;
    private String notes;
    private String image;
    private MultipartFile imageUpload;

}
