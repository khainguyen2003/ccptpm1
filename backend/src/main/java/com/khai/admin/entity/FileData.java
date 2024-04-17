package com.khai.admin.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tblFile")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String type;
    private String path;
    private long size;
    // Id của đối tượng cần nối
    private int related_id;
    // Tên đối tượng cần nối như user hay email
    private String related_type;
}
