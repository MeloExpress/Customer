package br.com.MeloExpress.Customer.repository;

import br.com.MeloExpress.Customer.domain.Address;
import br.com.MeloExpress.Customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByAddressIdAndCustomerCustomerIdAndCustomerActiveTrue(Long addressId, Long customerId);
    Optional<Address> findByAddressCode(UUID addressCode);
    List<Address> findByCustomer(Customer customer);

}

