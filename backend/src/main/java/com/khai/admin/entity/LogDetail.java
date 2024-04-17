package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tbllog_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogDetail {
    @EmbeddedId
    private LogDetailKey key;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", referencedColumnName = "log_id")
    private Log log;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean readed;
    @Column(columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean deleted;
}

@Embeddable
class LogDetailKey {
    @Column(name = "ld_id")
    private UUID logId;
    @Column(name = "lg_user_id")
    private UUID userId;

    public LogDetailKey() {
    }

    public LogDetailKey(UUID logId, UUID userId) {
        this.logId = logId;
        this.userId = userId;
    }

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogDetailKey that = (LogDetailKey) o;
        return logId == that.logId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, userId);
    }

    @Override
    public String toString() {
        return "LogDetailKey{" +
                "logId=" + logId +
                ", userId=" + userId +
                '}';
    }
}
