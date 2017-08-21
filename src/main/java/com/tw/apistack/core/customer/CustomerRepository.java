package com.tw.apistack.core.customer;

/**
 * Created by jxzhong on 2017/7/17.
 */

import java.util.List;

import com.tw.apistack.core.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    List<Customer> findByLastNameContaining(String lastName);
    Page<Customer> findByLastNameContaining(String lastName, Pageable pageable);

    Page<Customer> findAll(Pageable pageable);

    @Modifying
    @Query("update Customer customer set customer.lastName = :lastName where customer.id = :id")
    @Transactional
    int changeLastNameById(@Param("id") Long id, @Param("lastName") String lastName);

    @Transactional
    int deleteCustomerById(@Param("id") Long id);
}
