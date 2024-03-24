package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

/** Thiết kế lớp
 * `workplace_id` int(11) NOT NULL AUTO_INCREMENT,
 *   `workplace_name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT 'Tên gian hàng',
 *   `workplace_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '1 là kho hàng, 2 là cửa hàng',
 *   `workplace_address` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Địa chỉ gian hàng',
 *   `workplace_status` smallint(1) NOT NULL DEFAULT '1' COMMENT 'Trạng thái của gian hàng',
 *   `workplace_manager_id` int(11) DEFAULT '0' COMMENT 'Người quản lý gian hàng',
 *   `workplace_website_link` varchar(45) DEFAULT NULL COMMENT 'Đường dẫn liên kết đến trang web làm việc',
 *   `workplace_map_link` varchar(45) DEFAULT NULL COMMENT 'Đường dẫn địa chỉ',
 *   `workplace_created_date` varchar(45) NOT NULL DEFAULT '1/1/2000' COMMENT 'Thời gian khởi tạo',
 *   `workplace_last_modified_id` int(11) DEFAULT NULL COMMENT 'Người chỉnh sửa lần cuối',
 *   `workplace_last_modified_date` varchar(45) DEFAULT NULL COMMENT 'Thời gian chỉnh sửa',
 *   `workplace_deleted` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT 'Trạng thái xóa',
 *   `workplace_images` varchar(100) DEFAULT NULL COMMENT 'Hình ảnh gian hàng',
 *   `workplace_investment` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Vốn đầu tư',
 *   `workplace_expected_profit` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Lợi nhuận mong muốn',
 *   `workplace_notes` text COMMENT 'Mô tả',
 *   `workplace_phone` varchar(15) DEFAULT NULL COMMENT 'Số điện thoại liên hệ',
 *   `workplace_email` varchar(45) DEFAULT NULL COMMENT 'Email liên hệ',
 *   `workplace_creator_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Người khởi tạo gian hàng',
 */

@Table(name = "tblworkplace")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workplace_id")
    private int id;
    @Column(name = "workplace_name", columnDefinition = "varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'Tên gian hàng'")
    private String name;
    @Column(name = "workplace_type", columnDefinition = "smallint(1) NOT NULL DEFAULT '0' COMMENT '1 là kho hàng, 2 là cửa hàng'")
    private byte type;
    @Column(name = "workplace_address", columnDefinition = "varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Địa chỉ gian hàng'")
    private String address;
    @Column(name = "workplace_status", columnDefinition = "smallint(1) NOT NULL DEFAULT '1' COMMENT 'Trạng thái của gian hàng'")
    private byte status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workplace_manager_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "workplace_website_link", columnDefinition = "varchar(100) DEFAULT NULL COMMENT 'Đường dẫn liên kết đến trang web làm việc'")
    private String website_link;
    @Column(name = "workplace_map_link", columnDefinition = "varchar(100) DEFAULT NULL COMMENT 'Đường dẫn địa chỉ'")
    private String map_link;
    @CreatedDate
    @Column(name = "workplace_created_date")
    private Date created_date;
    @ManyToOne
    @JoinColumn(name = "workplace_last_modified_id")
    private User last_modified_by;
    @LastModifiedDate
    @Column(name = "workplace_last_modified_date")
    private Date last_modified_time;
    @Column(name = "workplace_deleted", columnDefinition = "BIT(1) DEFAULT b'0' COMMENT 'Trạng thái xóa'")
    private byte isDeleted;
    @Column(name = "workplace_images", columnDefinition = "varchar(255) DEFAULT NULL COMMENT 'Hình ảnh gian hàng'")
    private String images;
    @Column(name = "workplace_investment", columnDefinition = "int unsigned NOT NULL DEFAULT '0' COMMENT 'Vốn đầu tư'")
    private int investment;
    @Column(name = "workplace_expected_profit")
    private int experted_profit;
    @Column(name = "workplace_notes")
    private String notes;
    @Column(name = "workplace_phone")
    private String phone;
    @Column(name = "workplace_email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "workplace_creator_id")
    private User creator;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "workplace")
    private List<WorkplaceDetail> wpdList;

}
