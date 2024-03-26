package com.khai.admin.service;

import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.WorkplaceDto;
import com.khai.admin.entity.Workplace;
import com.khai.admin.entity.WorkplaceDetail;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.WorkplaceDetailRepository;
import com.khai.admin.repository.WorkplaceRepository;
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
public class WorkplaceService {
    private WorkplaceRepository workplaceRepository;
    private WorkplaceDetailRepository workplaceDetailRepository;

    @Autowired
    public WorkplaceService(WorkplaceRepository workplaceRepository, WorkplaceDetailRepository workplaceDetailRepository) {
        this.workplaceRepository = workplaceRepository;
        this.workplaceDetailRepository = workplaceDetailRepository;
    }

    @Transactional
    public WorkplaceDto create(WorkplaceDto workplaceDto){
        Optional<Workplace> workplaceOpt = this.workplaceRepository.findByName(workplaceDto.getName());
        if (workplaceOpt.isPresent()){
            throw new AlreadyExist("Chi nhánh", workplaceDto.getName());
        }

        Workplace workplace = new Workplace();
        workplaceDto.applyToEntity(workplace);
        Date date = new Date();
        workplace.setCreated_date(date);
        workplace.setLast_modified_time(date);
        this.workplaceRepository.save(workplace);
        return new WorkplaceDto(workplace);
    }

    @Transactional
    public WorkplaceDto update(WorkplaceDto workplaceDto){
        Optional<Workplace> workplaceOpt = this.workplaceRepository.findById(workplaceDto.getId());

        if (workplaceOpt.isPresent()){
            Workplace workplace = workplaceOpt.get();
            if (!workplace.getName().equalsIgnoreCase(workplaceDto.getName())){
                workplaceDto.applyToEntity(workplace);
                this.workplaceRepository.save(workplace);
                return new WorkplaceDto(workplace);
            } else {
                throw new AlreadyExist("Chi nhánh", workplaceDto.getName());
            }
        } else {
            throw new NoSuchElementException("Chi nhánh","id",""+workplaceDto.getId());
        }
    }

    public void detele(WorkplaceDto workplaceDto){
        Optional<Workplace> workplaceOpt = this.workplaceRepository.findById(workplaceDto.getId());

        if (workplaceOpt.isPresent()){
            Workplace workplace = workplaceOpt.get();
            if (!workplace.getName().equalsIgnoreCase(workplaceDto.getName())){
                workplaceDto.applyToEntity(workplace);
                this.workplaceRepository.delete(workplace);
            } else {
                throw new AlreadyExist("Chi nhánh", workplaceDto.getName());
            }
        } else {
            throw new NoSuchElementException("Chi nhánh","id",""+workplaceDto.getId());
        }
    }

    public void delete(int id){

    }
    public Map<String,Object> getWorkplaces(Pageable pageable){
        Page<Workplace> workplacePage = this.workplaceRepository.findAll(pageable);

        List<WorkplaceDto> workplaceDtoList = new ArrayList<>();

        workplacePage.getContent().stream().map(WorkplaceDto::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();

        response.put("workplace_list", workplaceDtoList);
        response.put("currentPage", pageable.getPageNumber());
        response.put("pageSize",pageable.getPageSize());
        response.put("totalPage", workplacePage.getTotalPages());

        return response;
    }

    public WorkplaceDto getWorkplaceById(int id){
        Optional<Workplace> workplaceOpt = this.workplaceRepository.findById(id);
        if (workplaceOpt.isPresent()){
            return new WorkplaceDto(workplaceOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy gian hàng");
        }
    }




}
