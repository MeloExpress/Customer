package br.com.MeloExpress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUpdateDTO{

    private String zipCode;
    private String street;
    private String number;
    private String complements;
    private String district;
    private String city;
    private String state;
    private String pointReference;
}
