package br.com.MeloExpress.dto;

import java.util.List;

public record CustomerRegisterDTO(

    String companyName,
    String cnpj,
    String stateRegistration,
    String email,
    String phone,
    String responsible,
    boolean active,
    List<AddressRegisterDTO> addresses

) {}
