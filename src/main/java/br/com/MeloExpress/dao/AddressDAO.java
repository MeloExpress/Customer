package br.com.MeloExpress.dao;

import br.com.MeloExpress.domain.Address;
import br.com.MeloExpress.domain.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressDAO extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.customer = :customer AND a.active = true")
    List<Address> findByCustomer(@Param("customer") Customer customer);


    @Query("SELECT a.*\n" +
            "FROM Address a \n" +
            "INNER JOIN Customer c ON c.customerId = a.customer_id\n" +
            "WHERE a.address_id = :addressId AND c.customer_id = :customerId AND a.active = true")
    Optional<Address> findByIdAndCustomer(@Param("addressId") Long addressId, @Param("customerId") Long customerId);



}

