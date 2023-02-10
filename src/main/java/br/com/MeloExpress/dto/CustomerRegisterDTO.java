package br.com.MeloExpress.dto;

import br.com.MeloExpress.domain.Customer;

public record CustomerRegisterDTO(

String companyName,
String cnpj,
String stateRegistration,
String email,
String phone,
String responsible

) {}
