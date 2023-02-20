package br.com.MeloExpress.dto;

import br.com.MeloExpress.domain.Address;

public record AddressDetailsDTO(Long addressId, Long customerId, String zipCode, String street, String number, String complements, String district, String city, String state, String pointReference) {

    public AddressDetailsDTO (Address address) {
        this (address.getCustomer().getCustomerId(), address.getAddressId(), address.getZipCode(), address.getStreet(), address.getNumber(), address.getComplements(), address.getDistrict(), address.getCity(), address.getState(), address.getPointReference());
    }

}
