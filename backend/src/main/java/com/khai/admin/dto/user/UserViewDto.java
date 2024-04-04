package com.khai.admin.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

public class UserViewDto implements Serializable {
    private int id;
    private String firstName;
    private String lastname;
    private String jobarea;
    private String position;

    public UserViewDto() {
    }

    public UserViewDto(int id, String firstName, String lastname, String jobarea, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.jobarea = jobarea;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJobarea() {
        return jobarea;
    }

    public void setJobarea(String jobarea) {
        this.jobarea = jobarea;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserViewDto that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
