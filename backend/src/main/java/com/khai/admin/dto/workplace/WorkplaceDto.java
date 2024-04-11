package com.khai.admin.dto.workplace;

import com.khai.admin.dto.workplace.WorkplaceDetailDto;
import com.khai.admin.entity.User;

import java.util.Date;
import java.util.List;

public class WorkplaceDto {
    private int id;
    private String name;
    private byte type;
    private String address;
    private byte status;
    private User user;
    private String website_link;
    private String map_link;
    private Date created_date;
    private User last_modified_by;
    private Date last_modified_time;
    private byte isDeleted;
    private String images;
    private int investment;
    private int experted_profit;
    private String notes;
    private String phone;
    private String email;
    private User creator;
    private List<WorkplaceDetailDto> wpdList;
}
