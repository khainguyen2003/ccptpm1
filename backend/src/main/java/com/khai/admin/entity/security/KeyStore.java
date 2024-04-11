package com.khai.admin.entity.security;

import com.khai.admin.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "tblkeyStore")
@Getter
public class KeyStore {
    @Id
    @Column(name = "user_id")
    private int userId;
    @Column(nullable = false)
    private String publicKey;
    @Column(nullable = false)
    private String privateKey;

    private List<String> refreshTokensUsed;
    @Column(nullable = false)
    private String refreshToken;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public KeyStore() {
    }

    public KeyStore(int user_id, String publicKey) {
        this.userId = user_id;
        this.publicKey = publicKey;
    }
}
