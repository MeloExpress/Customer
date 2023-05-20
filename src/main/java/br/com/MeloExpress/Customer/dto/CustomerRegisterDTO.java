package br.com.MeloExpress.Customer.dto;

import java.util.List;
import java.util.UUID;

public record CustomerRegisterDTO(



    String companyName,
    String cnpj,
    String stateRegistration,
    String email,
    String phone,
    String responsible,
    boolean active,
    List<AddressRegisterDTO> addresses,
    UUID customerCode

) {}
