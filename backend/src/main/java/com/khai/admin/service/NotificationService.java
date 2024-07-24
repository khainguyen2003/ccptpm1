package com.khai.admin.service;

import com.khai.admin.constants.NOTIFICATION_TYPE;
import com.khai.admin.constants.NotificationStatus;
import com.khai.admin.entity.Notification;
import com.khai.admin.repository.jpa.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification pushNotiToSystem(NOTIFICATION_TYPE notification_type, UUID senderId, String receiveId, Map<String, Object> more_details, NotificationStatus status) {
        Date now = new Date();
        String noti_content = "";
        switch(notification_type) {
            case SHOP_001 -> {
                noti_content = "@@@ vừa mới thêm một sản phẩm: @@@@";
                break;
            }
            case PROMOTION_001 -> {
                noti_content = "@@@ vừa mới thêm một voucher: @@@@@@";
                break;
            }
            default -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "notification type không hợp lệ");
            }
        }
        Notification newNotification = new Notification(notification_type, status, senderId, receiveId, noti_content, more_details, now, now);
        notificationRepository.save(newNotification);
        return newNotification;
    }
}
