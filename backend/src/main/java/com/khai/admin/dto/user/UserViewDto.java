package com.khai.admin.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserViewDto {
    private int id;
    private String firstName;
    private String lastname;
    private String jobarea;
    private String position;

    public UserViewDto(int id, String firstName, String lastname, String jobarea, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.jobarea = jobarea;
        this.position = position;
    }
}
