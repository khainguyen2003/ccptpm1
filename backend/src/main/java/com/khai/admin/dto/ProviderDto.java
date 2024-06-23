package com.khai.admin.dto;

import com.khai.admin.entity.Provider;
import com.khai.admin.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class ProviderDto {
    private int id ;
    private String name ;
    private String notes ;
    private String email;
    private String phone;
    private String address;
    private double currenr_debt; // Số tiền nợ
    private User creator;
    public ProviderDto(){

    }
    public ProviderDto(Provider provider){
        this.id = provider.getId();
        if(!provider.getName().isBlank()){
            this.name=provider.getName();
        }

        this.notes = provider.getNotes();
        this.email = provider.getEmail();
        this.phone = provider.getPhone();
        this.address = provider.getAddress();
        this.currenr_debt = provider.getCurrenr_debt();
        this.creator = provider.getCreator();
    }
    public void loadFromSupplier(Provider provider){
        this.id = provider.getId();
        if(!provider.getName().isBlank()){
            this.name=provider.getName();
        }
        this.notes = provider.getNotes();
        this.email = provider.getEmail();
        this.phone = provider.getPhone();
        this.address = provider.getAddress();
        this.currenr_debt = provider.getCurrenr_debt();
        this.creator = provider.getCreator();

    }
    public void updateToSupplier (Provider provider ){
        this.id = provider.getId();
        if(!provider.getName().isBlank()){
            this.name=provider.getName();
        }
        this.notes = provider.getNotes();
        this.email = provider.getEmail();
        this.phone = provider.getPhone();
        this.address = provider.getAddress();
        this.currenr_debt = provider.getCurrenr_debt();
        this.creator = provider.getCreator();
    }
    public void fromEntity (Provider provider ){
        provider.setId(this.id);
        if(!provider.getName().isBlank()){
            this.name=provider.getName();
        }
        provider.setNotes(this.notes);
        provider.setEmail(this.email);
        provider.setPhone(this.phone);
        provider.setAddress(this.address);
        provider.setCurrenr_debt(this.currenr_debt);
        provider.setCreator(this.creator);
    }
}
