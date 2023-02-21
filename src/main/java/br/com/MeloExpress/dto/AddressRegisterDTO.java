package br.com.MeloExpress.dto;

import lombok.Getter;
import lombok.Setter;

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
