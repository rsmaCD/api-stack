package com.tw.apistack.endpoint.customer;

import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.model.Customer;
import com.tw.apistack.endpoint.customer.dto.AddressDto;
import com.tw.apistack.endpoint.customer.dto.CustomerDto;
import com.tw.apistack.endpoint.customer.dto.ResourceWithUrl;
import com.tw.apistack.service.AddressService;
import com.tw.apistack.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by rsma on 18/08/2017.
 */
@RestController
@RequestMapping("/customers")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/search/lastName/{last-name}/{page}")
    public HttpEntity getTest(@PathVariable("last-name") String lastName, @PathVariable("page") int pageNum) {
        List<CustomerDto> byLastName = customerService.getByLastNameContaining(lastName, pageNum);
        return ResponseEntity.ok(byLastName);
    }

    @GetMapping
    public HttpEntity<List<ResourceWithUrl<CustomerDto>>> getAll() {
        List<CustomerDto> customerDtos = customerService.getAll();
        List<ResourceWithUrl<CustomerDto>> resourceWithUrls = new ArrayList<>();
        if (customerDtos.size() != 0) {
            resourceWithUrls = customerDtos.stream()
                    .map(this::getCustomerWithUrl)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(resourceWithUrls);
    }

    @GetMapping("/{customer-id}")
    public HttpEntity getById(@PathVariable("customer-id") Long id) {
        CustomerDto customerDto = customerService.getById(id);
        if (customerDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(getCustomerWithUrl(customerDto));
    }

    @GetMapping("/page/{page-num}")
    public HttpEntity getByPage(@PathVariable("page-num") int pageNum) {
        List<ResourceWithUrl<CustomerDto>> resourceWithUrls = customerService.getByPage(pageNum)
                .stream()
                .map(this::getCustomerWithUrl)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resourceWithUrls);
    }

    @PostMapping
    public HttpEntity save(@RequestBody Customer customer) {
        if (customerService.save(customer)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{customer-id}")
    public HttpEntity update(@PathVariable("customer-id") Long id, @RequestBody Customer customer) {
        if (customerService.updateById(id, customer)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{customer-id}")
    public HttpEntity delete(@PathVariable("customer-id") Long id) {
        if (customerService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{customer-id}/addresses")
    public HttpEntity getAddressesByCustomerId(@PathVariable("customer-id") Long id) {
        List<AddressDto> addressesByCustomerId = customerService.getAddressesByCustomerId(id);
        if (addressesByCustomerId != null) {
            List<ResourceWithUrl<AddressDto>> resourceWithUrls = addressesByCustomerId
                    .stream()
                    .map(addressDto -> getAddressWithUrl(id, addressDto))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resourceWithUrls);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{customer-id}/addresses/{address-id}")
    public HttpEntity getAddressByCustomerIdAndAddressId(@PathVariable("customer-id") Long customerId, @PathVariable("address-id") Long addressId) {
        AddressDto addressDto = addressService.getByCustomerIdAndAddressId(customerId, addressId);
        if (addressDto != null) {
            return ResponseEntity.ok(getAddressWithUrl(customerId,addressDto));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/{customer-id}/addresses")
    public HttpEntity addAddress(@PathVariable("customer-id") Long customerId, @RequestBody Address address) {
        if (addressService.addAddress(customerId, address)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{customer-id}/addresses/{address-id}")
    public HttpEntity modifyAddressByCustomerIdAndAddressId(@PathVariable("customer-id") Long customerId, @PathVariable("address-id") Long addressId, @RequestBody Address address) {
        if (addressService.modifyAddressByCustomerIdAndAddressId(customerId, addressId, address)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{customer-id}/addresses/{address-id}")
    public HttpEntity deleteAddressByCustomerIdAndAddressId(@PathVariable("customer-id") Long customerId, @PathVariable("address-id") Long addressId) {
        if (addressService.deleteAddressByCustomerIdAndAddressId(customerId, addressId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private ResourceWithUrl<CustomerDto> getCustomerWithUrl(CustomerDto customerDto) {
        ResourceWithUrl<CustomerDto> resourceWithUrl = new ResourceWithUrl<>(customerDto);
        resourceWithUrl.add(linkTo(methodOn(this.getClass()).getById(customerDto.getId())).withSelfRel());
        resourceWithUrl.add(linkTo(methodOn(this.getClass()).getAddressesByCustomerId(customerDto.getId())).withRel("addresses"));
        return resourceWithUrl;
    }

    private ResourceWithUrl<AddressDto> getAddressWithUrl(Long CustomerId, AddressDto addressDto) {
        ResourceWithUrl<AddressDto> resourceWithUrl = new ResourceWithUrl<>(addressDto);
        resourceWithUrl.add(linkTo(methodOn(this.getClass()).getAddressByCustomerIdAndAddressId(CustomerId, addressDto.getId())).withSelfRel());
        return resourceWithUrl;
    }
}
