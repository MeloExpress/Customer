package br.com.MeloExpress.dto;

import br.com.MeloExpress.domain.Customer;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerDetailsFindAllDTO(
        Long customerId,
        String companyName,
        String cnpj,
        String stateRegistration,
        String email, String phone,
        String responsible) {

    public CustomerDetailsFindAllDTO(Customer customer){
        this(customer.getCustomerId(),
                customer.getCompanyName(),
                customer.getCnpj(),
                customer.getStateRegistration(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getResponsible());
    }
}
