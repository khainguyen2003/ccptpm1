package com.khai.admin.entity;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khai.admin.dto.CustomerDto;
import com.khai.admin.dto.user.UserView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tblcustomer")
public class Customer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone ;
    private String notes;
    private String gender;
    private String address;
    private Date birthday;
    private double current_debt ;//Nợ hiện tại
    private double total ; //Tổng bán
    private int type;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User creator ;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ExportBill> bill ;
    public void applyToCustomer(Customer customer) {
        if(!customer.getName().isBlank()) {
            this.name = customer.getName();
        }
        this.phone = customer.getPhone();
        this.notes = customer.getNotes();
        this.gender = customer.getGender();
        this.address = customer.getAddress();
        this.birthday = customer.getBirthday();
        this.current_debt = customer.getCurrent_debt();
        this.total = customer.getTotal();
        this.type = customer.getType();
        this.creator = customer.getCreator();
        this.bill = customer.getBill();
    }
    public CustomerDto toDto() {
        UserView createdBy = new UserView();
        createdBy.loadFromUser(creator);
        return new CustomerDto(this);
    }

}