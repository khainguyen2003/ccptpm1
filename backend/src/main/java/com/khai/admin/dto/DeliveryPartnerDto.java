package com.khai.admin.dto;

import com.khai.admin.entity.DeliveryPartner;
import com.khai.admin.entity.Product;
import lombok.Getter;
import lombok.Setter;
public class DeliveryPartnerDto {
    private String id;
    private String name ;
    private String notes ;
    private String phone ;
    private String email ;
    private String address;
    private String type ;
    public DeliveryPartnerDto() {
    }

    public DeliveryPartnerDto(DeliveryPartner deliveryPartner) {
        this.id = deliveryPartner.getId();
        if(!deliveryPartner.getName().isBlank()) {
            this.name = deliveryPartner.getName();
        }
        this.notes = deliveryPartner.getNotes();
        this.email = deliveryPartner.getEmail();
        this.phone = deliveryPartner.getPhone();
        this.address = deliveryPartner.getAddress();
        this.type = deliveryPartner.getType();
    }

    private void loadFromDeliveryPartner(DeliveryPartner deliveryPartner) {
        this.id = deliveryPartner.getId();
        if(!deliveryPartner.getName().isBlank()) {
            this.name = deliveryPartner.getName();
        }
        this.notes = deliveryPartner.getNotes();
        this.phone = deliveryPartner.getPhone();
        this.email = deliveryPartner.getEmail();
        this.address = deliveryPartner.getAddress();
        this.type = deliveryPartner.getType();
    }

    public void updateToDeliveryPartner(DeliveryPartner deliveryPartner) {
        deliveryPartner.setId(id);
        if(!deliveryPartner.getName().isBlank()) {
            deliveryPartner.setName(name);
        }
        deliveryPartner.setNotes(notes);
        deliveryPartner.setPhone(phone);
        deliveryPartner.setEmail(email);
        deliveryPartner.setAddress(address);
        deliveryPartner.setType(type);
    }


}
