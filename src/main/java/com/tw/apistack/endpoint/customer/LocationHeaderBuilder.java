package com.tw.apistack.endpoint.customer;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by rsma on 20/08/2017.
 */
public class LocationHeaderBuilder {
    public static URI buildCustomerAddressLocation(int id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
