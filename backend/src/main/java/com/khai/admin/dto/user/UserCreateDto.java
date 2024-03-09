package com.khai.admin.dto.user;

import com.khai.admin.constants.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole userRole;

    @Override
    public String toString() {
        return "UserCreateDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}


