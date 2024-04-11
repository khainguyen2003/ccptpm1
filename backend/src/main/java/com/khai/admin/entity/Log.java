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
