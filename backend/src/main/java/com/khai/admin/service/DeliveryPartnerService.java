package com.khai.admin.service;

import com.khai.admin.dto.DeliveryPartnerDto;

import com.khai.admin.entity.DeliveryPartner;

import com.khai.admin.entity.Product;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.DeliveryPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
@Service
public class DeliveryPartnerService {
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public DeliveryPartner create(Map<String, String> headers, DeliveryPartner deliveryPartner) {
        Date now = new Date();
        if(deliveryPartnerRepository.findByName(deliveryPartner.getName()).isPresent()) {
            throw new AlreadyExist("Đối tác giao hàng", deliveryPartner.getName());
        }

        System.out.println(deliveryPartner);

        return deliveryPartnerRepository.save(deliveryPartner);
    }
    @Transactional
    public void importData(List<DeliveryPartner> data) {
        try {
            for (DeliveryPartner item : data) {
                System.out.println(item);

            }
        }catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    public DeliveryPartner getDeliveryPartnerById(int id) {
        Optional<DeliveryPartner> optDeliveryPartner = deliveryPartnerRepository.findById(id);
        if(optDeliveryPartner.isPresent()) {
            return optDeliveryPartner.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy đối tác giao hàng có id " + id);
        }
    }
    public DeliveryPartnerDto getDeliveryPartnerInfo(int id) {
        Optional<DeliveryPartner> deliveryPartner= deliveryPartnerRepository.findById(id);
        if(deliveryPartner.isPresent()) {
            return deliveryPartner.get().toDto();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có id" + id);
    }
    public Map<String, Object> getDeliveryPartners(String name, Pageable pageable) {
        try {
            List<DeliveryPartner> deliveryPartners = new ArrayList<>();
            Page<DeliveryPartner> pageDeliveryPartners;
            if(name == null) {
                pageDeliveryPartners = deliveryPartnerRepository.findAll(pageable);
            } else {
                pageDeliveryPartners = deliveryPartnerRepository.findByNameContaining(name, pageable);
            }
            deliveryPartners = pageDeliveryPartners.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("deliveryPartners", deliveryPartners);
            response.put("curPage", pageDeliveryPartners.getNumber());
            response.put("totalPage", pageDeliveryPartners.getTotalPages());
            response.put("totalElements", pageDeliveryPartners.getTotalElements());
            response.put("pageSize", pageDeliveryPartners.getSize());
            response.put("numberOfElements", pageDeliveryPartners.getNumberOfElements());
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public void deleteById(int id) {
        try {
            deliveryPartnerRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public DeliveryPartner updateDeliveryPartner(int id, DeliveryPartner updatedDeliveryPartner) {
        try {
            Optional<DeliveryPartner> optDeliveryPartner = deliveryPartnerRepository.findById(id);
            if(optDeliveryPartner.isPresent()) {
                DeliveryPartner deliveryPartner = optDeliveryPartner.get();
                updatedDeliveryPartner.applyToDeliveryPartner(deliveryPartner);
                deliveryPartnerRepository.save(deliveryPartner);
                return deliveryPartner;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @Transactional
    public DeliveryPartnerDto updateSellStatus(int id, DeliveryPartnerDto deliveryPartnerDto) {
        try {
            Optional<DeliveryPartner> deliveryPartner = deliveryPartnerRepository.findById(id);
            if(!deliveryPartner.isPresent()) {
                throw new NoSuchElementException("Sản phẩm", "id", String.valueOf(id));
            }
            DeliveryPartner updatedDeliveryPartner = deliveryPartner.get();

            deliveryPartnerRepository.save(updatedDeliveryPartner);
            return updatedDeliveryPartner.toDto();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
