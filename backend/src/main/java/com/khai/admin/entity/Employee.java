package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

import com.khai.admin.dto.EmployeeDto;

@Entity
@Table(name = "tblemployee")
@Data
public class Employee {
    /*
    `employee_id` int(11) NOT NULL,
  `employee_contract_expired_date` varchar(15) DEFAULT NULL COMMENT 'Ngày kết thúc hợp đồng',
  `employee_status` smallint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Trạng thái nhân viên (1-Đang làm; 0-Đã nghỉ )\r\n',
  `employee_work_time_length` varchar(15) DEFAULT NULL COMMENT 'Thời gian làm việc',
  `employee_workplace_start_time` varchar(15) DEFAULT NULL COMMENT 'Thời gian bắt đầu làm việc',
  `employee_workplace_id` int(11) DEFAULT NULL COMMENT '0- Kho hàng; 1 Cửa hàng',
  `employee_role` smallint(2) unsigned NOT NULL DEFAULT '0' COMMENT '(0-Nhân viên; 1-Quản lý gian hàng đơn vị; 2-Quản lý cấp cao)',
  `employee_created_date` varchar(45) NOT NULL COMMENT 'Ngày khởi tạo nhân viên',
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", columnDefinition = "INT(11)")
    private int id;
    @Column(name="employee_contract_expired_date", columnDefinition = "DATETIME(6) DEFAULT NULL COMMENT 'Ngày kết thúc hợp đồng'")
    private Date contract_expire;
    @Column(name="employee_status", columnDefinition = "smallint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Trạng thái nhân viên (1-Đang làm; 0-Đã nghỉ )\\r\\n'")
    private byte status;
    @Column(name="employee_branch_start_date")
    private Date start_date;
    @Column(name="employee_role", columnDefinition = "smallint(2) unsigned NOT NULL DEFAULT '0' COMMENT '(0-Nhân viên; 1-Quản lý gian hàng đơn vị; 2-Quản lý cấp cao)'")
    private byte role;
    @Column(name="employee_created_date", columnDefinition = "DATETIME(6) NOT NULL COMMENT 'Ngày khởi tạo nhân viên'")
    private Date created_date;
    @Column(name="employee_modified_date", columnDefinition = "DATETIME(6) NOT NULL COMMENT 'Ngày chỉnh sửa thông tin nhân viên'")
    private Date modified_date;
    @Column(name="employee_deleted", columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean deleted;
    @Column(name = "employee_fullname")
    private String fullname;
    @Column(name="employee_birthday", columnDefinition = "DATETIME(6) NOT NULL COMMENT 'Ngày sinh'")
    private Date birthday;
    @Column(name = "employee_email")
    private String email;
    @Column(name = "employee_phone")
    private String phoneNumber;
    @Column(name = "employee_address")
    private String address;
    @Column(name = "employee_notes")
    private String notes;
    @Column(name = "employee_image")
    private String image;
    @Column(name = "employee_name")
    private String name;
    @Column(name = "employee_pass")
    private String pass;

    public EmployeeDto getDto() {
      EmployeeDto e = new EmployeeDto();
      e.setId(id);
      e.setContract_expire(contract_expire);
      e.setStatus(status);
      e.setStart_date(start_date);
      e.setRole(role);
      e.setCreated_date(created_date);
      e.setModified_date(modified_date);
      e.setDeleted(deleted);
      e.setFullname(fullname);
      e.setBirthday(birthday);
      e.setEmail(email);
      e.setPhoneNumber(phoneNumber);
      e.setNotes(notes);
      e.setImage(image);
      e.setAddress(address);
      e.setName(fullname);
      e.setPass(pass);
      return e;
    }

}
