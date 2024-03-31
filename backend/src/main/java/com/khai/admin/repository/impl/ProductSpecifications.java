package com.khai.admin.repository.impl;

import com.khai.admin.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ProductSpecifications {
    public static Specification<Product> selectFields() {
        return (root, query, criteriaBuilder) -> {
            query.multiselect(
                    root,
                    root.join("creator").get("id"),
                    root.join("creator").get("firstname"),
                    root.join("creator").get("lastname"),
                    root.join("creator").get("job"),
                    root.join("creator").get("position"),
                    root.join("category").get("id"),
                    root.join("category").get("name")
            );
            return null; // return null because we are only interested in multiselect
        };
    }

    public static Specification<Product> hasSearch(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.<String>get("name"), "%" + search + "%"),
                criteriaBuilder.like(root.<String>get("code"), "%" + search + "%")
        );
    }

    public static Specification<Product> allowSale(byte allowSale) {
        if(allowSale == 1) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDirectCell"), 1);
        } else if (allowSale == 0) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDirectCell"), 0);
        }

        return null;
    }

    /**
     * Dự kiến hết hàng
     * @param stockOutDate alltime | other | timeString (threeDayLater)
     * @param stockoutStartDate
     * @param stockoutEndDate
     * @return
     */
    public static Specification<Product> stockOut(String stockOutDate, Date stockoutStartDate, Date stockoutEndDate) {
        return null;
    }

    /**
     * Lọc tồn kho
     * @param onHandFilter 0-all|1-con hàng|2-hết hàng|4-expression
     * @param onHandFilterStr biểu thức giá trị (>=10, <10)
     * @return
     */
    public static Specification<Product> onHand(byte onHandFilter, String onHandFilterStr) {
        return null;
    }
}
