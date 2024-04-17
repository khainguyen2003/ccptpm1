package com.khai.admin.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tblbd")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bd_id")
    private UUID id;
    @Column(name = "bd_bill_id")
    private UUID bill_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bd_product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "bd_product_quantity")
    private int quantity;
    @Column(name = "bd_product_price")
    private int price;
    @Column(name = "bd_product_discount", columnDefinition = "DECIMAL(10, 5) DEFAULT 0")
    private float discount;
    @Column(name = "bill_type", columnDefinition = "TINYINT(1)")
    private byte type;
}
