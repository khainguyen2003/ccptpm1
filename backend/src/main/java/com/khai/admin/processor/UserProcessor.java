package com.khai.admin.processor;

import com.khai.admin.entity.User;
import org.springframework.batch.item.ItemProcessor;

public class UserProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User u) throws Exception {
        if(!u.getFirstname().isBlank()) {
            return u;
        }
        return null;
    }
}
