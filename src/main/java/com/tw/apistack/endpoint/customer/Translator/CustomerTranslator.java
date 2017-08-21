package com.tw.apistack.endpoint.customer.Translator;

import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.model.Customer;
import com.tw.apistack.endpoint.customer.dto.AddressDto;
import com.tw.apistack.endpoint.customer.dto.CustomerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rsma on 20/08/2017.
 */
public class CustomerTranslator {
    public static CustomerDto translateCustomerModelToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;
    }

    public static List<AddressDto> translateAddressesModelToDto(Set<Address> addresses){
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto addressDto = new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setCity(address.getCity());
            addressDtos.add(addressDto);
        }
        return addressDtos;
    }

    public static AddressDto translateAddressModelToDto(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        return addressDto;
    }
}
