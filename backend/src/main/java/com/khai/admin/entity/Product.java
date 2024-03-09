package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "tblproduct")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_desc")
    private String description;
    @Column(name = "product_images")
    private String images;
    @CreatedDate
    @Column(name = "product_created_date")
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_created_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private boolean deleted;
    @Column(name = "prooduct_code", length = 100)
    private String code;
    @Column(name = "product_rate", columnDefinition = "tinyint default 0")
    private float rate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "pc_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;
}
