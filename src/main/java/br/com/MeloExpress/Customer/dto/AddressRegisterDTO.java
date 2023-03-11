package br.com.MeloExpress.Customer.dto;


import java.util.UUID;

public record AddressRegisterDTO(

        Long customerId,
        UUID addressCode,
        String zipCode,
        String street,
        String number,
        String complements,
        String district,
        String city,
        String state,
        String pointReference
) {}
