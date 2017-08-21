package com.tw.apistack.service.resourceValidator;

import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.CustomerRepository;
import com.tw.apistack.core.customer.model.Customer;
import com.tw.apistack.exception.ResourceNotExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rsma on 21/08/2017.
 */
@Component
public class CustomerValidator {

    @Autowired
    private CustomerRepository customerRepository;
    private static String CustomerNotExitExceptionMsg = "Customer with id=%s does not exist";
    private static String AddressNotExitExceptionMsg = "Address with id=%s does not exist";

    public Customer validateCustomerExistAndReturn(Long customerId){
        Customer customerById = customerRepository.findOne(customerId);
        if (customerById == null) {
            throw new ResourceNotExitException(String.format(CustomerNotExitExceptionMsg,customerId));
        }
        return customerById;
    }

    public Address validateCustomerAndAddressExistAndReturn(Long customerId, Long addressId){
        Customer customerById = customerRepository.findOne(customerId);
        if (customerById == null) {
            throw new ResourceNotExitException(String.format(CustomerNotExitExceptionMsg,customerId));
        }
        List<Address> addresses = customerById.getAddressSet()
                .stream()
                .filter(addressOne -> addressOne.getId().equals(addressId))
                .collect(Collectors.toList());
        if(addresses.size() == 0){
            throw new ResourceNotExitException(String.format(AddressNotExitExceptionMsg,addressId));
        }
        return addresses.get(0);
    }
}
