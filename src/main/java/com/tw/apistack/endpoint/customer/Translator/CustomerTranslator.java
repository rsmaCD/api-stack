package com.tw.apistack.endpoint.customer.Translator;

import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.model.Customer;
import com.tw.apistack.endpoint.customer.dto.AddressDto;
import com.tw.apistack.endpoint.customer.dto.CustomerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by rsma on 20/08/2017.
 */
@Component
public class CustomerTranslator {

    @Autowired
    private ModelMapper modelMapper;
    public CustomerDto translateCustomerModelToDto(Customer customer){
        return modelMapper.map(customer,CustomerDto.class);
    }

    public Customer translateCustomerDtoToModel(CustomerDto customerDto){
        return modelMapper.map(customerDto,Customer.class);
    }

    public List<AddressDto> translateAddressesModelToDto(Set<Address> addresses){
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto addressDto = modelMapper.map(address,AddressDto.class);
            addressDtos.add(addressDto);
        }
        return addressDtos;
    }

    public AddressDto translateAddressModelToDto(Address address){
        return modelMapper.map(address,AddressDto.class);
    }

    public Address translateAddressDtoToModel(AddressDto addressDto){
        return modelMapper.map(addressDto,Address.class);
    }
}
