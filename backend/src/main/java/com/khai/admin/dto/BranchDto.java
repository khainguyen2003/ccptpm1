package com.khai.admin.dto;

import com.khai.admin.entity.User;
import com.khai.admin.entity.Branch;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BranchDto {
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

    public BranchDto(Branch branch){
        this.id = branch.getId();
        if (!branch.getName().isBlank()){
            this.name = branch.getName();
        }
        this.type = branch.getType();

    }

    public BranchDto() {
    }

    public void applyToEntity(Branch branch){
        if (!this.name.isBlank()){
            branch.setName(this.name);
        }
    }
}
