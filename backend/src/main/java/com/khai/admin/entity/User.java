package com.khai.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khai.admin.constants.UserRole;
import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.security.KeyStore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tbluser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private int id;
    @Column(name = "user_name", unique = true)
    private String username; // tên đăng nhập
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_pass", nullable = false)
    private String password;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "user_address")
    private String address;
    @Column(name = "user_job")
    private String job;
    @Column(name = "user_position")
    private String position;
    private String avatar;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "last_modified")
    private Date lastModified;
    @Column(columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean deleted;
    @Column(columnDefinition = "BIT(1) DEFAULT b'1'")
    private boolean active;
    @Column(name = "apply_year")
    private short applyYear;
    private Date birthday;
    private String phone;
    private String note;
    @Column(name = "user_gender")
    private String gender;
    @Column(name="user_permission", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int permission;
    @Column(name="user_roles")
    @Enumerated(EnumType.STRING)
    private UserRole role;

//    mappedBy = "<Tên thuộc tính của LogDetail"
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<LogDetail> logDetailList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KeyStore keyStore;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Log> logCreatedList;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> createdProduct;

    @OneToMany(mappedBy = "last_modified_by", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Workplace> lastModifiedWorkplace;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Workplace> createdWorkplace;

    public UserView toUserview() {
        UserView userView = new UserView();
        userView.loadFromUser(this);
        return userView;
    }
}
