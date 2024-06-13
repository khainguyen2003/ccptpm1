package com.khai.admin.entity;

import com.khai.admin.dto.DeliveryPartnerDto;
import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.user.UserView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbldelivery_partner")
public class DeliveryPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name ;
    private String notes ;
    private String phone ;
    private String email ;
    private String address;
    private String type ;

    public void applyToDeliveryPartner(DeliveryPartner deliveryPartner) {
        if(!deliveryPartner.getName().isBlank()) {
            this.name = deliveryPartner.getName();
        }

        this.notes = deliveryPartner.getNotes();
        this.phone = deliveryPartner.getPhone();
        this.email = deliveryPartner.getEmail();
        this.address = deliveryPartner.getAddress();
        this.type = deliveryPartner.getType();
    }
    public DeliveryPartnerDto toDto() {

        return new DeliveryPartnerDto(this);
    }
}