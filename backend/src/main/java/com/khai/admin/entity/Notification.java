package com.khai.admin.entity;

import com.khai.admin.constants.NOTIFICATION_TYPE;
import com.khai.admin.constants.NotificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "tblnotification")
@Getter
@Setter
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
    private String noti_recieveId;
    @Column(nullable = false)
    private String noti_content;
    // dạng k: "<key>", v: "<value>"
//    private Map<String, Object> noti_more_details;
    private Date noti_created_on;
    private Date noti_updated_at;

    public Notification() {
    }

    public Notification(NOTIFICATION_TYPE noti_type, NotificationStatus status, UUID noti_senderID, String noti_recieveId, String noti_content, Map<String, Object> noti_more_details, Date noti_created_on, Date noti_updated_at) {
        this.noti_type = noti_type;
        this.status = status;
        this.noti_senderID = noti_senderID;
        this.noti_recieveId = noti_recieveId;
        this.noti_content = noti_content;
//        this.noti_more_details = noti_more_details;
        this.noti_created_on = noti_created_on;
        this.noti_updated_at = noti_updated_at;
    }
}
