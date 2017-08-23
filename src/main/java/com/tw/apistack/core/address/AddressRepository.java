package com.tw.apistack.core.address;

import com.tw.apistack.core.address.model.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsma on 20/08/2017.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {

}
