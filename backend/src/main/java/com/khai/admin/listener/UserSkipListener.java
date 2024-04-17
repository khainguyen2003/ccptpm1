package com.khai.admin.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khai.admin.entity.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class UserSkipListener implements SkipListener<User, Number> {
    @Override // item reader
    public void onSkipInRead(Throwable throwable) {
        log.info("A failure on read {} ", throwable.getMessage());
    }

    @Override // item writter
    public void onSkipInWrite(Number item, Throwable throwable) {
        log.info("A failure on write {} , {}", throwable.getMessage(), item);
    }

    @SneakyThrows
    @Override // item processor
    public void onSkipInProcess(User user, Throwable throwable) {
        log.info("Item {}  was skipped due to the exception  {}", new ObjectMapper().writeValueAsString(user),
                throwable.getMessage());
    }
}
