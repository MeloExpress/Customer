package br.com.MeloExpress.dto;

import br.com.MeloExpress.domain.Customer;

public record CustomerDetailsFindDTO(
        Long customerId,
        String companyName,
        String cnpj,
        String stateRegistration,
        String email, String phone,
        String responsible) {

    public CustomerDetailsFindDTO(Customer customer){
        this(customer.getCustomerId(),
                customer.getCompanyName(),
                customer.getCnpj(),
                customer.getStateRegistration(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getResponsible());
    }
}