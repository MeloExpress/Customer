package br.com.MeloExpress.dao;

import br.com.MeloExpress.domain.Address;
import br.com.MeloExpress.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT a FROM Address a WHERE a.customer = :customer AND a.active = true", nativeQuery = true)
    List<Address> findByCustomer(@Param("customer") Customer customer);


    @Query(value = "SELECT a" +
            "FROM Address AS a" +
            "INNER JOIN Customer c ON c.customer_id = a.customer_id" +
            "WHERE a.address_id = :addressId AND c.customer_id = :customerId AND c.active = true", nativeQuery = true)
    Optional<Address> findByIdAndCustomer(@Param("addressId") Long addressId, @Param("customerId") Long customerId);



}

