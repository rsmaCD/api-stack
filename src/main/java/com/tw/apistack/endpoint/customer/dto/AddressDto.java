package com.tw.apistack.endpoint.customer.dto;

/**
 * Created by rsma on 20/08/2017.
 */
public class AddressDto {

    private Long id;
    private String city;

    public AddressDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
