package com.tw.apistack.core.address;

import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by rsma on 20/08/2017.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
    @Transactional
    int deleteAddressById(@Param("id") Long id);

}
