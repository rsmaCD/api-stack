package com.tw.apistack.endpoint.customer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by rsma on 20/08/2017.
 */
public class ResourceWithUrl<T> extends ResourceSupport {

    private final T content;

    @JsonCreator
    public ResourceWithUrl(@JsonProperty("content") T content) {
        this.content = content;
    }

    @JsonUnwrapped
    public T getContent() {
        return content;
    }
}
