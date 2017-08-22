package com.tw.apistack.service;

import com.google.common.collect.Lists;
import com.tw.apistack.core.customer.CustomerRepository;
import com.tw.apistack.core.customer.model.Customer;
import com.tw.apistack.endpoint.customer.Translator.CustomerTranslator;
import com.tw.apistack.endpoint.customer.dto.AddressDto;
import com.tw.apistack.endpoint.customer.dto.CustomerDto;
import com.tw.apistack.service.resourceValidator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rsma on 20/08/2017.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerValidator customerValidator;
    private static final int PAGE_SIZE = 1;
    @Autowired
    private CustomerTranslator customerTranslator;

    public List<CustomerDto> getByLastNameContaining(String lastName, int pageNum) {
        return Lists.newArrayList(customerRepository.findByLastNameContaining(lastName, new PageRequest(pageNum, PAGE_SIZE)))
                .stream()
                .map(customerTranslator::translateCustomerModelToDto)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> getAll() {
        return Lists.newArrayList(customerRepository.findAll())
                .stream()
                .map(customerTranslator::translateCustomerModelToDto)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> getByPage(int pageNum) {
        return Lists.newArrayList(customerRepository.findAll(new PageRequest(pageNum, PAGE_SIZE)))
                .stream()
                .map(customerTranslator::translateCustomerModelToDto)
                .collect(Collectors.toList());
    }

    public boolean save(Customer customer) {
        Customer save = customerRepository.save(customer);
        return save != null;
    }

    public boolean updateById(Long id, Customer customer) {
        Customer customerById = customerValidator.validateCustomerExistAndReturn(id);
        Customer customerMerged = customerById.merge(customer);
        Customer save = customerRepository.save(customerMerged);
        return save != null;
    }

    public boolean deleteById(Long id) {
        customerValidator.validateCustomerExistAndReturn(id);
        int deleteCustomerById = customerRepository.deleteCustomerById(id);
        return deleteCustomerById != 0;
    }


    public List<AddressDto> getAddressesByCustomerId(Long id){
        Customer customerById = customerValidator.validateCustomerExistAndReturn(id);
        return customerTranslator.translateAddressesModelToDto(customerById.getAddressSet());
    }

    public CustomerDto getById(Long id) {
        Customer customerById = customerValidator.validateCustomerExistAndReturn(id);
        return customerTranslator.translateCustomerModelToDto(customerById);
    }
}
