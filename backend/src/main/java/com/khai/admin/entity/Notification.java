package com.khai.admin.entity;

import com.khai.admin.constants.NOTIFICATION_TYPE;
import com.khai.admin.constants.NotificationStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tblnotification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID noti_id;

    /*
        ORDER_001, //ORDER SUCCESS
        ORDER_002, // ORDER FAIL
        PROMOTION_001, // NEW PROMOTION
        SHOP_001 // NEW PRODUCT BY USER FOLLOWING
    */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NOTIFICATION_TYPE noti_type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status = NotificationStatus.UNREAD;
    @Column(nullable = false)
    private UUID noti_senderID;
    @Column(nullable = false)
    private UUID noti_recieveId;
    @Column(nullable = false)
    private String noti_content;
    private byte noti_options;
    private Date noti_created_on;
    private Date noti_updated_at;
}
