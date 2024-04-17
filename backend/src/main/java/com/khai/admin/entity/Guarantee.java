package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="tblguarantee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guarantee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="gu_start_time")
    private Date start_time;
    @Column(name="gu_end_time")
    private Date end_time;
    @Column(name="gu_price")
    private int price;
    @Column(name="gu_method")
    private byte method;

    public void applyToGurantee(Guarantee guarantee) {
        this.start_time = guarantee.getStart_time();
        this.end_time = guarantee.getEnd_time();
        this.price = guarantee.getPrice();
        this.method = guarantee.getMethod();
    }
}
