package com.khai.admin.dto;

import com.khai.admin.entity.User;
import com.khai.admin.entity.Workplace;
import com.khai.admin.entity.WorkplaceDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
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

    public WorkplaceDto(Workplace workplace){
        this.id = workplace.getId();
        if (!workplace.getName().isBlank()){
            this.name = workplace.getName();
        }
        this.type = workplace.getType();

    }

    public WorkplaceDto() {
    }

    public void applyToEntity(Workplace workplace){
        if (!this.name.isBlank()){
            workplace.setName(this.name);
        }
    }
}
