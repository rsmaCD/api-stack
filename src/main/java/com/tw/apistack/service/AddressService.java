package com.tw.apistack.service;

import com.google.common.collect.Lists;
import com.tw.apistack.core.address.AddressRepository;
import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.CustomerRepository;
import com.tw.apistack.core.customer.model.Customer;
import com.tw.apistack.endpoint.customer.Translator.CustomerTranslator;
import com.tw.apistack.endpoint.customer.dto.AddressDto;
import com.tw.apistack.service.resourceValidator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by rsma on 20/08/2017.
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerValidator customerValidator;
    @Autowired
    private CustomerTranslator customerTranslator;

    public AddressDto getByCustomerIdAndAddressId(Long customerId, Long addressId){
        Address address = customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        return customerTranslator.translateAddressModelToDto(address);
    }

    public boolean addAddress(Long customerId, AddressDto addressDto) {
        Customer customer = customerValidator.validateCustomerExistAndReturn(customerId);
        Address address = customerTranslator.translateAddressDtoToModel(addressDto);
        Address addressSave = addressRepository.save(address);
        customer.getAddressSet().add(addressSave);
        Customer save = customerRepository.save(customer);
        return save != null;
    }

    public boolean modifyAddressByCustomerIdAndAddressId(Long customerId, Long addressId, AddressDto addressDto){
        Address oldAddress = customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        Address address = customerTranslator.translateAddressDtoToModel(addressDto);
        return addressRepository.save(oldAddress.merge(address)) != null;
    }

    @Transactional
    public void deleteAddressByCustomerIdAndAddressId(Long customerId, Long addressId) {
        customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        Customer customer = customerRepository.findOne(customerId);
        customer.getAddressSet().removeIf(address -> address.getId().equals(addressId));
        customerRepository.save(customer);
        addressRepository.delete(addressId);
    }

    public List<Address> getAddresses(){
        return Lists.newArrayList(addressRepository.findAll());
    }
}
