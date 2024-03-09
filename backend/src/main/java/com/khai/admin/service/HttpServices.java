package com.khai.admin.service;

import com.khai.admin.dto.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HttpServices {
    private SearchCriteria criteria;
    public List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        return orders;
    }
    /**
     * Phương thức chuyển giá trị direction trên đường dẫn thành kiểu Sort.Direction
     * @param direction giá trị sx trên đường dẫn (asc, desc)
     * @return
     */
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

//    public Predicate toPredicate(
//            Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//
//        switch (criteria.getOperation()) {
//            case EQUALITY:
//                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
//            case NEGATION:
//                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
//            case GREATER_THAN:
//                return builder.greaterThan(root.<String> get(
//                        criteria.getKey()), criteria.getValue().toString());
//            case LESS_THAN:
//                return builder.lessThan(root.<String> get(
//                        criteria.getKey()), criteria.getValue().toString());
//            case LIKE:
//                return builder.like(root.<String> get(
//                        criteria.getKey()), criteria.getValue().toString());
//            case STARTS_WITH:
//                return builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
//            case ENDS_WITH:
//                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
//            case CONTAINS:
//                return builder.like(root.<String> get(
//                        criteria.getKey()), "%" + criteria.getValue() + "%");
//            default:
//                return null;
//        }
//    }
}
