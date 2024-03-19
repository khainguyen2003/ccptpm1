package com.khai.admin.service;

import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.Log;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final UserService userService;

    @Autowired
    public LogService(LogRepository logRepository, UserService userService) {
        this.logRepository = logRepository;
        this.userService = userService;
    }

    public Map<String, Object> getLogs(Pageable pageable) {
        try {
            Page<Log> pageLogs = logRepository.findAll(pageable);
            List<Log> logs = pageLogs.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("logs", logs);

            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau. " + e.getMessage());
        }
    }

    public void updateReadStatus(int id) {
        String username = UserService.getCurrentUsername();
        UserView user = userService.getUserInfoByEmail(username);
        try {
            logRepository.updateReadById(id, user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau. " + e.getMessage());
        }
    }

    public Log createLog(int creator_id, byte action, byte position, byte permission) {
        Log log = Log.builder()
                .

        return null;
    }
}
