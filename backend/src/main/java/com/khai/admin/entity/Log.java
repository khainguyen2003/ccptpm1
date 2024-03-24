package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbllog")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Log {

    /*
    `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `log_creator_id` varchar(45) NOT NULL COMMENT 'ID người khởi tạo',
  `log_user_permission` smallint(1) unsigned NOT NULL COMMENT 'Quyền của người dùng',
  `log_username` varchar(45) NOT NULL COMMENT 'Tên người dùng',
  `log_action` smallint(1) unsigned NOT NULL COMMENT '1-Add, 2- Edit, 3-Del',
  `log_position` smallint(50) unsigned NOT NULL COMMENT 'Vị trí bảng (1-bill, 2-bd, 3-employee. 4-guarantee, 5-product, 6-provider, 7-user, 8-workplace, 9-wsd)',
  `log_name` text COMMENT 'Tên log',
  `log_notes` text COMMENT 'Ghi chú của log',
  `log_created_date` varchar(45) DEFAULT NULL COMMENT 'Ngày khởi tạo Log',
  `log_readed` bit(1) DEFAULT '0' COMMENT 'Log đã đọc chưa'
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "log_creator_id")
    private User creator;
    @Column(name="log_user_permission", columnDefinition = "smallint(1) unsigned NOT NULL COMMENT 'Quyền của người dùng'")
    private byte permis;
    @Column(name="log_username")
    private String username;
    @Column(name="log_action", columnDefinition = "smallint(1) unsigned NOT NULL COMMENT '1-Add, 2- Edit, 3-Del'")
    private byte action;
    @Column(name="log_position")
    private String postion;
    @Column(name = "log_notes")
    private String notes;
    @CreatedDate
    @Column(name = "log_created_date")
    private Date created_time;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "log")
    private List<LogDetail> logDetailList;

    public void applyToLog(Log log) {
        this.creator = log.getCreator();
        this.permis = log.getPermis();
        this.username = log.getUsername();
        this.action = log.getAction();
        this.postion = log.getPostion();
        this.notes = log.getNotes();
        this.created_time = log.getCreated_time();
    }
}
