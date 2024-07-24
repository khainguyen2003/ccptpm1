package com.khai.admin.dto.user;

import com.khai.admin.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserViewDto implements Serializable {
    private UUID id;
    private String firstName;
    private String lastname;
    private String jobarea;
    private String position;

    public UserViewDto() {
    }
    public UserViewDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstname();
        this.lastname = user.getLastname();
        this.jobarea = user.getJobArea();
        this.position = user.getPosition();
    }

    public UserViewDto(UUID id, String firstName, String lastname, String jobarea, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.jobarea = jobarea;
        this.position = position;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public void fromEntity(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstname();
        this.lastname = user.getLastname();
        this.jobarea = user.getJobArea();
        this.position = user.getPosition();
    }
}
