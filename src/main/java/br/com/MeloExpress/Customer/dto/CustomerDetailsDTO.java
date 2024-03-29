package br.com.MeloExpress.Customer.dto;

import br.com.MeloExpress.Customer.domain.Customer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record CustomerDetailsDTO(
        Long customerId,
        UUID customerCode,
        String active,
        String companyName,
        String cnpj,
        String stateRegistration,
        String email, String phone,
        String responsible,
        List<AddressDetailsDTO> addresses) {



    public CustomerDetailsDTO (Customer customer){
        this(customer.getCustomerId(),
                customer.getCustomerCode(),
                String.valueOf(customer.isActive()),
                customer.getCompanyName(),
                customer.getCnpj(),
                customer.getStateRegistration(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getResponsible(),
                customer.getAddresses().stream()
                        .map(AddressDetailsDTO::new)
                        .collect(Collectors.toList()));
    }
}
