package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

/** Thiết kế lớp
 * `branch_id` int(11) NOT NULL AUTO_INCREMENT,
 *   `branch_name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT 'Tên gian hàng',
 *   `branch_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '1 là kho hàng, 2 là cửa hàng',
 *   `branch_address` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Địa chỉ gian hàng',
 *   `branch_status` smallint(1) NOT NULL DEFAULT '1' COMMENT 'Trạng thái của gian hàng',
 *   `branch_manager_id` int(11) DEFAULT '0' COMMENT 'Người quản lý gian hàng',
 *   `branch_website_link` varchar(45) DEFAULT NULL COMMENT 'Đường dẫn liên kết đến trang web làm việc',
 *   `branch_map_link` varchar(45) DEFAULT NULL COMMENT 'Đường dẫn địa chỉ',
 *   `branch_created_date` varchar(45) NOT NULL DEFAULT '1/1/2000' COMMENT 'Thời gian khởi tạo',
 *   `branch_last_modified_id` int(11) DEFAULT NULL COMMENT 'Người chỉnh sửa lần cuối',
 *   `branch_last_modified_date` varchar(45) DEFAULT NULL COMMENT 'Thời gian chỉnh sửa',
 *   `branch_deleted` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT 'Trạng thái xóa',
 *   `branch_images` varchar(100) DEFAULT NULL COMMENT 'Hình ảnh gian hàng',
 *   `branch_investment` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Vốn đầu tư',
 *   `branch_expected_profit` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Lợi nhuận mong muốn',
 *   `branch_notes` text COMMENT 'Mô tả',
 *   `branch_phone` varchar(15) DEFAULT NULL COMMENT 'Số điện thoại liên hệ',
 *   `branch_email` varchar(45) DEFAULT NULL COMMENT 'Email liên hệ',
 *   `branch_creator_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Người khởi tạo gian hàng',
 */

@Table(name = "tblbranch")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private int id;
    @Column(name = "branch_name", columnDefinition = "varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'Tên gian hàng'")
    private String name;
    @Column(name = "branch_type", columnDefinition = "smallint(1) NOT NULL DEFAULT '0' COMMENT '1 là kho hàng, 2 là cửa hàng'")
    private byte type;
    @Column(name = "branch_address", columnDefinition = "varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Địa chỉ gian hàng'")
    private String address;
    @Column(name = "branch_status", columnDefinition = "smallint(1) NOT NULL DEFAULT '1' COMMENT 'Trạng thái của gian hàng'")
    private byte status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_manager_id", referencedColumnName = "user_id")
    private User manager;
    @Column(name = "branch_website_link", columnDefinition = "varchar(100) DEFAULT NULL COMMENT 'Đường dẫn liên kết đến trang web làm việc'")
    private String website_link;
    @Column(name = "branch_map_link", columnDefinition = "varchar(100) DEFAULT NULL COMMENT 'Đường dẫn địa chỉ'")
    private String map_link;
    @ManyToOne
    @JoinColumn(name = "branch_creator_id")
    private User creator;
    @CreatedDate
    @Column(name = "branch_created_date")
    private Date created_date;
    @ManyToOne
    @JoinColumn(name = "branch_last_modified_id")
    private User last_modified_by;
    @LastModifiedDate
    @Column(name = "branch_last_modified_date")
    private Date last_modified_date;
    @Column(name = "branch_deleted", columnDefinition = "BIT(1) DEFAULT b'0' COMMENT 'Trạng thái xóa'")
    private byte is_deleted;
    @Column(name = "branch_images", columnDefinition = "varchar(255) DEFAULT NULL COMMENT 'Hình ảnh gian hàng'")
    private String images;
    @Column(name = "branch_investment", columnDefinition = "int unsigned NOT NULL DEFAULT '0' COMMENT 'Vốn đầu tư'")
    private int investment;
    @Column(name = "branch_expected_profit")
    private int expected_profit;
    @Column(name = "branch_notes")
    private String notes;
    @Column(name = "branch_phone")
    private String phone;
    @Column(name = "branch_email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "branch")
    private List<BranchDetail> wpdList;

}
