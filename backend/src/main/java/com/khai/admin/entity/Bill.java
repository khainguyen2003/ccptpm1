package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tblbill")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int id;
    @Column(name = "bill_status", columnDefinition = "smallint(1) default 0")
    private byte status;

    @Column(name = "bill_last_modified_date")
    private Date last_modified_date;
    @Column(name = "bill_last_modified_id")
    private String last_modified_id;
    @Column(name = "bill_created_date")
    private Date created_date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;
    @Column(name = "bill_transporter_id")
    private int transporter_id;
    @Column(name = "bill_type")
    private String type;
    @Column(name = "bi_target_workplace_id")
    private int bi_provider_id;
    @Column(name = "be_customer_id")
    private User be_customer_id;
    @Column(name = "be_current_workplace_id")
    private int be_current_workplace_id;
    @Column(name = "be_target_address")
    private String be_target_address;

}
