package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tbl_resource")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int file_id;

    private String file_name;
    private String folder;
    private String public_id;
    private String asset_id;
    private String resource_type;
    private Date create_at;

}
