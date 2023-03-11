package br.com.MeloExpress.Customer.repository;

import br.com.MeloExpress.Customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

}
