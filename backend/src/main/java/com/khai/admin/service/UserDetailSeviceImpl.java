package com.khai.admin.service;

import com.khai.admin.entity.User;
import com.khai.admin.entity.security.UserDetailsImpl;
import com.khai.admin.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Service
public class UserDetailSeviceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailSeviceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findFirstByEmail(username);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Tên người dùng không tồn tại");
        }
        User user = optionalUser.get();
        return new UserDetailsImpl(user);
    }
}
