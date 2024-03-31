package com.khai.admin.repository.impl;

import com.khai.admin.entity.Product;
import com.khai.admin.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    EntityManager em;
    @Override
    public Page<Object[]> getProducts(
        Pageable pageable,
        String search,
        byte allowSale,
        String stockOutDate,
        String stockoutStartDate,
        String stockoutEndDate,
        byte onHandFilter,
        String onHandFilterStr,
        int[] brandIds,
        byte directSell,
        byte relateToChanel
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if(search != null) { // giá trị tìm kiếm
            search = search.trim();
            if (!search.equalsIgnoreCase("")) {
                predicates.add(cb.or(cb.like(productRoot.get("id"), "%"+search+"%"), cb.like(productRoot.get("id"), "%"+search+"%")));
            }
        }

        if(allowSale == 1) { // cho phép kinh doanh
            predicates.add(cb.equal(productRoot.get("isDirectCell"), 1));
        } else if(allowSale == -1) {
            predicates.add(cb.equal(productRoot.get("isDirectCell"), 0));
        }

        return null;
    }
}
