package br.com.MeloExpress.dto;


public record AddressRegisterDTO(

        Long customerId,
        String zipCode,
        String street,
        String number,
        String complements,
        String district,
        String city,
        String state,
        String pointReference
) {}
