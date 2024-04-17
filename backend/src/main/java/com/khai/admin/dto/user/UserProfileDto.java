package com.khai.admin.dto.user;

import com.khai.admin.constants.UserRole;
import com.khai.admin.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserProfileDto {
    private UUID id;
    private String firstName;
    private String lastname;
    private Date birthday;
    private String phone;
    private String jobarea;
    private String position;
    private String email;
    private String user_address;
    private short user_applyyear;
    private String user_notes;
    private UserRole user_roles; // thuộc tính người dùng chọn
    private Date created_at;
    private Date last_modified;

    public void loadFromUser(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstname();
        this.lastname = user.getLastname();
        this.birthday = user.getBirthday();
        this.phone = user.getPhone();
        this.jobarea = user.getJob();
        this.position = user.getPosition();
        this.email = user.getEmail();
        this.user_address = user.getAddress();
        this.user_applyyear = user.getApplyYear();
        this.user_notes = user.getNote();
        this.user_roles = user.getRole();
        this.created_at = user.getCreatedAt();
        this.last_modified = user.getLastModified();
    }
}
