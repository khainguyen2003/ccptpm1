package com.khai.admin.entity.security;

import com.khai.admin.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="public_key", nullable = false, unique = true)
    private String publicKey;
    // dùng trong trường hợp access token bị hết hạn hoặc bị hack
    @Column(name="refresh_token", unique = true)
    private List<String> refreshToken;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Token(int user_id, String publicKey) {
        this.user.setId(user_id);
        this.publicKey = publicKey;
    }
}
