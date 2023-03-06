package br.com.MeloExpress.dto;

import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.domain.Address;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerDetailsDTO(
        Long customerId,
        String companyName,
        String cnpj,
        String stateRegistration,
        String email, String phone,
        String responsible,
        List<AddressDetailsDTO> addresses) {

    public CustomerDetailsDTO (Customer customer){
        this(customer.getCustomerId(),
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
