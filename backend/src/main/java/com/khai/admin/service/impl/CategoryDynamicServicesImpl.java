package com.khai.admin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khai.admin.dto.PaginationResponse;
import com.khai.admin.dto.category.CategoryDto;
import com.khai.admin.entity.Category;
import com.khai.admin.repository.jpa.CategoryRepository;
import com.khai.admin.request.FilterRequest;
import com.khai.admin.service.CategoryDynamicServices;
import com.khai.admin.util.DateService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryDynamicServicesImpl implements CategoryDynamicServices {
    private final CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private final EntityManager entityManager;
    public CategoryDynamicServicesImpl(CategoryRepository categoryRepository, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
    }

    @Override
    public PaginationResponse findByCriteria(FilterRequest filterRequest, Pageable pageable) {
        Specification<Category> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filterRequest.keyword() != null && !filterRequest.keyword().isBlank()) {
                String wildcard_keyword = "%" + filterRequest.keyword() + "%";
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.get("name"), wildcard_keyword),
                                criteriaBuilder.like(root.get("notes"), wildcard_keyword)
                        )
                );
            }

            if(filterRequest.fromDate() != null && !filterRequest.fromDate().isBlank()) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("last_modified"), DateService.getDate(filterRequest.fromDate()))
                );
            }

            if(filterRequest.toDate() != null && !filterRequest.toDate().isBlank()) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("last_modified"), DateService.getDate(filterRequest.toDate()))
                );
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };

        Page<Category> pageCategories = this.categoryRepository.findAll(spec, pageable);
        List<CategoryDto> categories = pageCategories.getContent().stream().map(item -> new CategoryDto(item)).collect(Collectors.toList());
        PaginationResponse response = PaginationResponse.builder()
                .totalElements(pageCategories.getTotalElements())
                .items(categories)
                .curPage(pageCategories.getNumber())
                .pageSize(pageCategories.getSize())
                .totalPage(pageCategories.getTotalPages())
                .numberOfElements(pageCategories.getNumberOfElements())
                .build();

        return response;
    }

    @Override
    @Transactional
    public PaginationResponse findByProcedure(FilterRequest filterRequest, Pageable pageable) {
        try {
            String str_con = objectMapper.writeValueAsString(filterRequest);
            System.out.println(str_con);
//            Page<Category> pageCategories = this.categoryRepository.findAllByProcedure(str_con, pageable.getPageSize(), pageable.getPageNumber());
            List<CategoryDto> categories = this.categoryRepository.findAllByProcedure(str_con, pageable.getPageSize(), pageable.getPageNumber()).stream().map(item -> new CategoryDto(item)).collect(Collectors.toList());
            PaginationResponse response = PaginationResponse.builder()
//                    .totalElements(pageCategories.getTotalElements())
                    .items(categories)
                    .curPage(pageable.getPageNumber())
                    .pageSize(pageable.getPageSize())
//                    .totalPage(pageCategories.getTotalPages())
                    .numberOfElements(categories.size())
                    .build();

            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaginationResponse findByEntityManager(FilterRequest filterRequest, int pageNum, int pageSize, List<Sort.Order> orders) {
        try {
            StringBuilder sql = new StringBuilder("SELECT c FROM Category c ");
            StringBuilder countSql = new StringBuilder("SELECT COUNT(c) FROM Category c ");
            StringBuilder str_condition = new StringBuilder();
            Map<String, Object> params = new HashMap<>();
            if(filterRequest.keyword() != null && !filterRequest.keyword().isBlank()) {
                if(!str_condition.isEmpty()) {
                    str_condition.append(" AND ");
                }
                str_condition.append(" (LOWER(name) LIKE LOWER(:keyword) OR LOWER(notes) LIKE LOWER(:keyword))");
                params.put("keyword", "%" + filterRequest.keyword() + "%");
            }

            if (filterRequest.fromDate() != null && !filterRequest.fromDate().equalsIgnoreCase("null") && !filterRequest.fromDate().isBlank()) {
                if(!str_condition.isEmpty()) {
                    str_condition.append(" AND ");
                }
                Date fromDate = DateService.getDate(filterRequest.fromDate());
                if (filterRequest.toDate() != null && !filterRequest.toDate().equalsIgnoreCase("null") && !filterRequest.toDate().isBlank()) {
                    Date toDate = DateService.getDate(filterRequest.toDate());
                    str_condition.append(" last_modified BETWEEN :fromDate AND :toDate");
                    params.put("fromDate", DateService.getDate(filterRequest.fromDate()));
                    params.put("toDate", DateService.getDate(filterRequest.toDate()));

                } else {
                    str_condition.append(" last_modified >= :fromDate");
                    params.put("fromDate", DateService.getDate(filterRequest.fromDate()));
                }
            }

            if (!str_condition.isEmpty()) {
                sql.append(" WHERE ").append(str_condition);
                countSql.append(" WHERE ").append(str_condition);
            }

            sql.append(" ORDER BY ");
            if (orders !=null && !orders.isEmpty()) {
                for (Sort.Order order : orders) {
                    sql.append(order.getProperty()).append(" ").append(order.getDirection()).append(" ");
                }
            } else {
                sql.append("last_modified DESC ");
            }

            System.out.println(sql.toString());
            System.out.println(countSql.toString());
            Query query = entityManager.createQuery(sql.toString(), Category.class);
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
            query.setFirstResult(pageNum * pageSize);
            query.setMaxResults(pageSize);

            Query countQuery = entityManager.createQuery(countSql.toString(), Category.class);
            for (String key : params.keySet()) {
                countQuery.setParameter(key, params.get(key));
            }
            int totalElements = Integer.valueOf(String.valueOf(countQuery.getResultList().getFirst()));
            int totalPages = (int)Math.ceil(totalElements / pageSize);
            List<Category> items = query.getResultList();

            List<CategoryDto> categories = items.stream().map(item -> new CategoryDto(item)).collect(Collectors.toList());
            PaginationResponse response = PaginationResponse.builder()
                    .totalElements(totalElements)
                    .items(categories)
                    .curPage(pageNum)
                    .pageSize(pageSize)
                    .totalPage(totalPages)
                    .numberOfElements(categories.size())
                    .build();

            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    public boolean deleteById(UUID id) {
        boolean isUpdated = false;
        Category foundedCategory = entityManager.find(Category.class, id);
        if(foundedCategory != null) {
            int updatedCount = entityManager.createQuery("UPDATE Category c SET c.parent = null WHERE c.parent = :parent")
                    .setParameter("parent", foundedCategory)
                    .executeUpdate();
            if(updatedCount > 0) {
                entityManager.remove(foundedCategory);
                isUpdated = true;
            }
        }

        return isUpdated;
    }
}
