package com.khai.admin.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tblbd")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bd_id")
    private int id;
    @Column(name = "bd_bill_id")
    private int bill_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bd_product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "bd_product_quantity")
    private int quantity;
    @Column(name = "bd_product_price")
    private int price;
    @Column(name = "bd_product_discount", columnDefinition = "DECIMAL(5, 10) DEFAULT 0")
    private float discount;
    @Column(name = "bill_type", columnDefinition = "TINYINT(1)")
    private byte type;
}
