package com.khai.admin.service;

import com.khai.admin.dto.BranchDto;
import com.khai.admin.entity.Branch;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.BranchDetailRepository;
import com.khai.admin.repository.BranchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
/*
*@param
 */
@Service
public class BranchService {
    private BranchRepository branchRepository;
    private BranchDetailRepository branchDetailRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository, BranchDetailRepository branchDetailRepository) {
        this.branchRepository = branchRepository;
        this.branchDetailRepository = branchDetailRepository;
    }

    @Transactional
    public BranchDto create(BranchDto branchDto){
        Optional<Branch> branchOpt = this.branchRepository.findByName(branchDto.getName());
        if (branchOpt.isPresent()){
            throw new AlreadyExist("Chi nhánh", branchDto.getName());
        }

        Branch branch = new Branch();
        branchDto.applyToEntity(branch);
        Date date = new Date();
        branch.setCreated_date(date);
        branch.setLast_modified_date(date);
        this.branchRepository.save(branch);
        return new BranchDto(branch);
    }

    @Transactional
    public BranchDto update(BranchDto branchDto){
        Optional<Branch> branchOpt = this.branchRepository.findById(branchDto.getId());

        if (branchOpt.isPresent()){
            Branch branch = branchOpt.get();
            if (!branch.getName().equalsIgnoreCase(branchDto.getName())){
                branchDto.applyToEntity(branch);
                this.branchRepository.save(branch);
                return new BranchDto(branch);
            } else {
                throw new AlreadyExist("Chi nhánh", branchDto.getName());
            }
        } else {
            throw new NoSuchElementException("Chi nhánh","id",""+ branchDto.getId());
        }
    }

    public void detele(BranchDto branchDto){
        Optional<Branch> branchOpt = this.branchRepository.findById(branchDto.getId());
        try {
            if (!branchOpt.isPresent()){
                throw new NoSuchElementException("Chi nhánh", "id", String.valueOf(branchDto.getId()));
            } else {
                Branch branch = branchOpt.get();
                this.branchRepository.delete(branch);
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public void delete(int id){
        Optional<Branch> branchOpt = this.branchRepository.findById(id);
        try {
            if (!branchOpt.isPresent()){
                throw new NoSuchElementException("Chi nhánh", "id", String.valueOf(id));
            } else {
                Branch branch = branchOpt.get();
                this.branchRepository.delete(branch);
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    public Map<String,Object> getBranchs(Pageable pageable){
        Page<Branch> branchPage = this.branchRepository.findAll(pageable);

        List<BranchDto> branchDtoList = new ArrayList<>();

        branchPage.getContent().stream().map(BranchDto::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();

        response.put("branchList", branchDtoList);
        response.put("currentPage", pageable.getPageNumber());
        response.put("pageSize",pageable.getPageSize());
        response.put("totalPage", branchPage.getTotalPages());

        return response;
    }

    public BranchDto getBranchById(int id){
        Optional<Branch> branchOpt = this.branchRepository.findById(id);
        if (branchOpt.isPresent()){
            return new BranchDto(branchOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy gian hàng");
        }
    }

    public Map<String,Object> getBranchsByCreatedDate(Date date1, Date date2 ,Pageable pageable){
        if (date1.after(date2)){
            Date tmp = date1;
            date1 = date2;
            date2 = tmp;
        }

        Page<Branch> branchPage = this.branchRepository.findByCreatedDate(date1,date2,pageable);

        List<BranchDto> branchDtoList = new ArrayList<>();

        branchPage.getContent().stream().map(BranchDto::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();

        response.put("branchList", branchDtoList);
        response.put("currentPage", pageable.getPageNumber());
        response.put("pageSize",pageable.getPageSize());
        response.put("totalPage", branchPage.getTotalPages());

        return response;
    }




}
