package com.khai.admin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tblFile")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private String path;
    private long size;
    // Id của đối tượng cần nối
    private int related_id;
    // Tên đối tượng cần nối như user hay email
    private String related_type;
}
