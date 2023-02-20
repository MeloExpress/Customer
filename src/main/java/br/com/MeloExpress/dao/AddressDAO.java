package br.com.MeloExpress.dao;

import br.com.MeloExpress.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AddressDAO extends JpaRepository<Address, Long> {
}
