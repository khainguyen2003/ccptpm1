package com.khai.admin.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tblbi")
public class ImportBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bi_id")
    private int id;
    @Column(name = "bill_status", columnDefinition = "smallint(1) default 0")
    private byte status;

    @Column(name = "bill_last_modified_date")
    private Date last_modified_date;
    @Column(name = "bi_last_modified_id")
    private String last_modified_id;
    @Column(name = "bi_created_date")
    private Date created_date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "bill_transporter_id")
    private int transporter_id;
    @Column(name = "bill_type")
    private String type;
    @Column(name = "bi_target_workplace_id")
    private int bi_provider_id;
}
