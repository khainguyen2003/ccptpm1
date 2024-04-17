package com.khai.admin.entity.security;

import com.khai.admin.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tblkeyStore")
@Getter
@Setter
@NoArgsConstructor
public class KeyStore {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(nullable = false)
    private byte[] publicKey;
    @Lob
    private byte[] privateKey;

    @Lob
    private List<byte[]> refreshTokensUsed;
    @Lob
    private String refreshToken;

}
