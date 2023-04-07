package br.com.MeloExpress.Customer.dto;

import br.com.MeloExpress.Customer.domain.Customer;

import java.util.UUID;

public record CustomerDetailsFindDTO(
        Long customerId,
        UUID customerCode,
        String companyName,
        String cnpj,
        String stateRegistration,
        String email, String phone,
        String responsible) {

    public CustomerDetailsFindDTO(Customer customer){
        this(customer.getCustomerId(),
                customer.getCustomerCode(),
                customer.getCompanyName(),
                customer.getCnpj(),
                customer.getStateRegistration(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getResponsible());
    }
}
