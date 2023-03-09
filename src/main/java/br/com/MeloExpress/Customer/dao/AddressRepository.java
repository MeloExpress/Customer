package br.com.MeloExpress.Customer.dao;

import br.com.MeloExpress.Customer.domain.Address;
import br.com.MeloExpress.Customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByAddressIdAndCustomerCustomerIdAndCustomerActiveTrue(Long addressId, Long customerId);
    List<Address> findByCustomer(Customer customer);

}

