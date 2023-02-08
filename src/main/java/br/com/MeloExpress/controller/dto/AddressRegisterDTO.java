package br.com.MeloExpress.controller.dto;

public record AddressRegisterDTO(

        String zipCode,
        String street,
        String number,
        String complements,
        String district,
        String city,
        String state,
        String pointReference
) {}
