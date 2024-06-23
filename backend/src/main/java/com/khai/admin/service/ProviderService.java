package com.khai.admin.service;


import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.ProviderDto;
import com.khai.admin.dto.user.UserView;
import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.user.UserEditDto;
import com.khai.admin.entity.Provider;
import com.khai.admin.entity.User;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.UserRepository;
import com.khai.admin.repository.ProviderRepository;
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
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;
    private UserService userService;

    @Autowired
    public ProviderService(
            ProviderRepository providerRepository,
            UserService userService
    ) {
        this.providerRepository = providerRepository;
        this.userService = userService;
    }
    public ProviderDto getProviderInfo(int id) {
        Optional<Provider> provider = providerRepository.findById(id);
        if(provider.isPresent()) {
            ProviderDto providerDto = new ProviderDto();
            providerDto.fromEntity(provider.get());
            return providerDto;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm có id" + id);
    }
    @Transactional
    public void importData(List<Provider> data) {
        try {
            for (Provider item : data) {
                System.out.println(item);
//                providerRepository.save(item);
            }
        }catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    public ProviderDto create(Map<String, String> headers, Provider provider) {
        try {
            Date now = new Date();
            if(providerRepository.isExist(provider.getName()).isPresent()) {
                throw new AlreadyExist("Nhà cung cấp");
            }
            User user = userService.getUserById(1);


            provider.setCreator(user);
            providerRepository.save(provider);
            return provider.toDto();
//            ProviderDto providerDto = new ProviderDto();
//            providerDto.fromEntity(provider.get());
//            return providerDto;

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    public Map<String, Object> getProvider(String name, Pageable pageable) {
        try {
            List<Provider> providers = new ArrayList<>();
            List<ProviderDto> providerDtoList = new ArrayList<>();
            Page<Provider> pageProviders;
//            if(name == null) {
            pageProviders = providerRepository.findAll(pageable);
//            }
//            } else {
//                pageProviders = providerRepository.findByNameContaining(name, pageable);
//            }
            providers = pageProviders.getContent();
            providers.stream().map(ProviderDto::new).forEach(providerDtoList::add);
            Map<String, Object> response = new HashMap<>();
            response.put("providers", providerDtoList);
            response.put("curPage", pageProviders.getNumber());
            response.put("totalPage", pageProviders.getTotalPages());
            response.put("totalElements", pageProviders.getTotalElements());
            response.put("pageSize", pageProviders.getSize());
            response.put("numberOfElements", pageProviders.getNumberOfElements());
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    @Transactional
    public void deleteById(int id) {
        try {
            Optional<Provider> providerOpt = providerRepository.findById(id);
            if(!providerOpt.isPresent()) {
                throw new NoSuchElementException("Nhà cung cấp", "id", String.valueOf(id));
            } else {
                Provider provider = providerOpt.get();

                    providerRepository.deleteById(id);

            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    @Transactional
    public ProviderDto updateProvider(int id, Provider updatedProvider) {
        try {
            Optional<Provider> optProvider = providerRepository.findById(id);
            if(optProvider.isPresent()) {
                Provider provider = optProvider.get();
                updatedProvider.applyToProvider(provider);
                providerRepository.save(provider);
                return provider.toDto();

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    public ProviderDto updateSellStatus(int id, ProviderDto providerDto) {
        try {
            Optional<Provider> provider = providerRepository.findById(id);
            if(!provider.isPresent()) {
                throw new NoSuchElementException("Nhà cung cấp", "id", String.valueOf(id));
            }
            Provider updatedProvider = provider.get();

            providerRepository.save(updatedProvider);
            return updatedProvider.toDto();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
