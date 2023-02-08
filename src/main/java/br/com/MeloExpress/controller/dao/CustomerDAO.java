package br.com.MeloExpress.controller.dao;

import br.com.MeloExpress.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<Customer, Long> {

}
