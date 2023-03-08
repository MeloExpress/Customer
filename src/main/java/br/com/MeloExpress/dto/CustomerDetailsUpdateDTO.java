package br.com.MeloExpress.dto;

import br.com.MeloExpress.domain.Customer;

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
