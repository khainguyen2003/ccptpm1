package com.khai.admin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tblkeyStore")
public class KeyStore {
    @Id
    private int user_id;
    private String publicKey;
    private String privateKey;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
