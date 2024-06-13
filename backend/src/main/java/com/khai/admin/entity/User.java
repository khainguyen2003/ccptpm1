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
    protected String username; // tên đăng nhập
    @Column(name = "user_email", unique = true)
    protected String email;
    @Column(name = "user_pass", nullable = false)
    protected String password;
    @Column(name = "first_name")
    protected String firstname;
    @Column(name = "last_name")
    protected String lastname;
    @Column(name = "user_address")
    protected String address;
    @Column(name = "user_job")
    protected String job;
    @Column(name = "user_position")
    protected String position;
    protected String avatar;
    @Column(name = "created_at")
    protected Date createdAt;
    @Column(name = "last_modified")
    protected Date lastModified;
    @Column(columnDefinition = "BIT(1) DEFAULT b'0'")
    protected boolean deleted;
    @Column(columnDefinition = "BIT(1) DEFAULT b'1'")
    protected boolean active;
    @Column(name = "apply_year")
    protected short applyYear;
    protected Date birthday;
    protected String phone;
    protected String note;
    @Column(name = "user_gender")
    protected String gender;
    @Column(name="user_permission", columnDefinition = "TINYINT(1) DEFAULT 0")
    protected int permission;
    @Column(name="user_roles")
    @Enumerated(EnumType.STRING)
    protected UserRole role;

//    mappedBy = "<Tên thuộc tính của LogDetail"
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    protected List<LogDetail> logDetailList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    protected KeyStore keyStore;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<Log> logCreatedList;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<Product> createdProduct;

    @OneToMany(mappedBy = "last_modified_by", fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<Workplace> lastModifiedWorkplace;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<Workplace> createdWorkplace;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Customer> createdCustomer;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Provider> createdSubplier;

    public UserView toUserview() {
        UserView userView = new UserView();
        userView.loadFromUser(this);
        return userView;
    }
}
