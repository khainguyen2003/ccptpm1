package com.khai.admin.dto.user;

import com.khai.admin.constants.UserRole;
import com.khai.admin.entity.User;

import java.util.Date;

public class UserEditDto {
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
    private UserRole user_roles;

    public void applyToUser(User user) {

        if(!this.email.isBlank()) {
            user.setEmail(email);
        }
        if(!this.firstName.isBlank()) {
            user.setFirstname(firstName);
        }
        user.setLastname(lastname);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setJob(jobarea);
        user.setPosition(position);
        user.setAddress(user_address);
        user.setNote(user_notes);
        user.setRole(user_roles);
        user.setApplyYear(user_applyyear);
        Date now = new Date();
        user.setLastModified(now);
    }
}
