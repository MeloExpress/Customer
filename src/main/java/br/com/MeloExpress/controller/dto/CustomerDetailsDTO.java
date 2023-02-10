package br.com.MeloExpress.controller.dto;

import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.domain.Address;

public record CustomerDetailsDTO(Long customerId, String companyName, String cnpj, String stateRegistration, String email, String phone, String responsible) {

    public CustomerDetailsDTO (Customer customer){
        this(customer.getCustomerId(), customer.getCompanyName(), customer.getCnpj(), customer.getStateRegistration(), customer.getEmail(), customer.getPhone(), customer.getResponsible());
    }
}
