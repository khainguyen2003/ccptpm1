package com.khai.admin.entity;

import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.ProviderDto;
import com.khai.admin.dto.user.UserView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tblprovider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String name ;
    private String notes ;
    private String email;
    private String phone;
    private String address;
    private double currenr_debt; // Số tiền nợ
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User creator;
    public void applyToProvider(Provider provider) {
        if(!provider.getName().isBlank()) {
            this.name = provider.getName();
        }
        this.notes = provider.getNotes();
        this.email = provider.getEmail();
        this.phone = provider.getPhone();
        this.address = provider.getAddress();
        this.currenr_debt = provider.getCurrenr_debt();
        this.creator = provider.getCreator();
    }
    public ProviderDto toDto() {
        UserView createdBy = new UserView();
        createdBy.loadFromUser(creator);
        return new ProviderDto(this);
    }

}