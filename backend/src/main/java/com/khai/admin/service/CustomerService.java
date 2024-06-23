package com.khai.admin.service;

import com.khai.admin.dto.CustomerDto;
import com.khai.admin.dto.ProductDto;
import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.Customer;
import com.khai.admin.entity.User;
import com.khai.admin.entity.ImportBill;
import com.khai.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.exception.NoSuchElementException;
import com.khai.admin.repository.ImportBillRepository;
import com.khai.admin.repository.CustomerRepository;
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
public class CustomerService {
    private CustomerRepository customerRepository;
    private UserService userService;
    private final ImportBillRepository importBillRepository;

    @Autowired
    public CustomerService(
            CustomerRepository customerRepository,

            UserService userService, ImportBillRepository importBillRepository
    ) {
        this.customerRepository = customerRepository;
        //this.importBillRepository = importBillRepository;
        this.userService = userService;
        this.importBillRepository = importBillRepository;
    }
    public CustomerDto getCustomerInfo(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            return customer.get().toDto();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng có id" + id);
    }

    @Transactional
    public void importData(List<Customer> data) {
        try {
            for (Customer item : data) {
                System.out.println(item);
//                productRepository.save(item);
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
    public CustomerDto create(Map<String, String> headers, Customer customer) {
        try {

            if(customerRepository.isExist(customer.getName()).isPresent()) {
                throw new AlreadyExist("Khách hàng");
            }
            User user = userService.getUserById(1);

            customer.setDeleted(false);
//            //customer.setCreatedDate(now);
            //ImportBill importBill = importBillRepository.findById(customer.getBill().get()).orElse(null);
//            if(customer != null)
//                customer.setBill((List<ImportBill>) importBill);
            customer.setCreator(user);
            customerRepository.save(customer);

            return customer.toDto();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Map<String, Object> getCustomers(String name, Pageable pageable) {
        try {
            List<Customer> customers = new ArrayList<>();
            List<CustomerDto> customerDtoList = new ArrayList<>();
            Page<Customer> pageCustomers;
//            if(name == null) {
            pageCustomers = customerRepository.findAll(pageable);
//            }
//            } else {
//                pageProducts = productRepository.findByNameContaining(name, pageable);
//            }
            customers = pageCustomers.getContent();
            customers.stream().map(CustomerDto::new).forEach(customerDtoList::add);
            Map<String, Object> response = new HashMap<>();
            response.put("customers", customerDtoList);
            response.put("curPage", pageCustomers.getNumber());
            response.put("totalPage", pageCustomers.getTotalPages());
            response.put("totalElements", pageCustomers.getTotalElements());
            response.put("pageSize", pageCustomers.getSize());
            response.put("numberOfElements", pageCustomers.getNumberOfElements());
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }

    @Transactional
    public void deleteById(int id) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(id);
            if(!customerOpt.isPresent()) {
                throw new NoSuchElementException("Khách hàng", "id", String.valueOf(id));
            } else {
                Customer customer = customerOpt.get();

                    customerRepository.deleteById(id);

            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Thực hiện không thành công. Vui lòng thử lại sau");
        }
    }
    @Transactional
    public CustomerDto updateCustomer(int id, Customer updatedCustomer) {
        try {
            Optional<Customer> optCustomer = customerRepository.findById(id);
            if(optCustomer.isPresent()) {
                Customer customer = optCustomer.get();
                updatedCustomer.applyToCustomer(customer);
                customerRepository.save(customer);
                return customer.toDto();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục có id " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @Transactional
    public CustomerDto updateSellStatus(int id, CustomerDto customerDto) {
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            if(!customer.isPresent()) {
                throw new NoSuchElementException("Khách hàng", "id", String.valueOf(id));
            }
            Customer updatedCustomer = customer.get();
            customerRepository.save(updatedCustomer);
            return updatedCustomer.toDto();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
