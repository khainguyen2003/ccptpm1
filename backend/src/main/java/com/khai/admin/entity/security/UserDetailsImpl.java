package com.khai.admin.entity.security;

import com.khai.admin.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if(user.getPermission() <= 1) {
//            return Collections.singleton(new SimpleGrantedAuthority("USER"));
//        }
//        return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));

        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public UUID getUserId() { return getUserId();}

    public User getCurrentUserLogin() {return this.user;}

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive() && !user.isDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive() && !user.isDeleted();
    }
}
