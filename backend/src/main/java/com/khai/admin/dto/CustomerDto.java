package com.khai.admin.dto;

import com.khai.admin.entity.Customer;
import com.khai.admin.entity.ExportBill;
import com.khai.admin.entity.Product;
import com.khai.admin.entity.User;

import java.util.Date;
import java.util.List;

public class CustomerDto {
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
    private User creator ;
    private List<ExportBill> bill ;
    public CustomerDto() {

    }

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
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
        this.creator = customer.getCreator();
        this.bill = customer.getBill();
    }
    private void loadFromCustomer(Customer customer) {
        this.id = customer.getId();
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
        this.creator = customer.getCreator();
        this.bill = customer.getBill();
    }
    public void updateToCustomer(Customer customer) {
        customer.setId(id);
        if(!customer.getName().isBlank()) {
            customer.setName(name);
        }
        this.phone = customer.getPhone();
        this.notes = customer.getNotes();
        this.gender = customer.getGender();
        this.address = customer.getAddress();
        this.birthday = customer.getBirthday();
        this.current_debt = customer.getCurrent_debt();
        this.total = customer.getTotal();
        this.creator = customer.getCreator();
        this.bill = customer.getBill();
    }
}
