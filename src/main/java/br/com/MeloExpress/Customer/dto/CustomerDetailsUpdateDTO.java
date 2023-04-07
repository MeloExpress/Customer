package br.com.MeloExpress.Customer.dto;

import br.com.MeloExpress.Customer.domain.Customer;

public record CustomerDetailsUpdateDTO(
        Long customerId,
        String companyName,
        String email,
        String phone,
        String responsible) {

    public CustomerDetailsUpdateDTO(Customer customer){
        this(customer.getCustomerId(),
                customer.getCompanyName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getResponsible());
    }
}
