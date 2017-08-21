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

    public AddressDto getByCustomerIdAndAddressId(Long customerId, Long addressId){
        Address address = customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        return CustomerTranslator.translateAddressModelToDto(address);
    }

    public boolean addAddress(Long customerId, Address address) {
        Customer customer = customerValidator.validateCustomerExistAndReturn(customerId);
        Address addressSave = addressRepository.save(address);
        customer.getAddressSet().add(addressSave);
        Customer save = customerRepository.save(customer);
        return save != null;
    }

    public boolean modifyAddressByCustomerIdAndAddressId(Long customerId, Long addressId, Address address){
        Address oldAddress = customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        return addressRepository.save(oldAddress.merge(address)) != null;
    }

    public boolean deleteAddressByCustomerIdAndAddressId(Long customerId, Long addressId) {
        customerValidator.validateCustomerAndAddressExistAndReturn(customerId, addressId);
        addressRepository.delete(addressId);
        return true;
    }

    public List<Address> getAddresses(){
        return Lists.newArrayList(addressRepository.findAll());
    }
}
