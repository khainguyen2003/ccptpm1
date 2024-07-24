package com.khai.admin.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class EmployeeDto {

    private UUID id;
    private Date contract_expire;
    private byte status;
    private Date start_date;
    private byte role;
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
    private String name;
    private String pass;

}
