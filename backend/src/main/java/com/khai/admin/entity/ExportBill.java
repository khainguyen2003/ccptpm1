package com.khai.admin.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tblbe")
public class ExportBill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "be_id")
    private UUID id;
    @Column(name = "be_status", columnDefinition = "smallint(1) default 0")
    private byte status;

    @Column(name = "be_last_modified_date")
    private Date last_modified_date;
    @Column(name = "be_last_modified_id")
    private String last_modified_id;
    @Column(name = "be_created_date")
    private Date created_date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "be_customer_id", referencedColumnName = "user_id")
    private User be_customer_id;
    @Column(name = "be_current_workplace_id")
    private UUID be_current_workplace_id;
    @Column(name = "be_target_address")
    private String be_target_address;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bd_id", referencedColumnName = "be_id")
    private List<BillDetail> billDetails;
}
