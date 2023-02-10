package br.com.MeloExpress.controller.dto;

import br.com.MeloExpress.domain.Customer;

public record CustomerRegisterDTO(

String companyName,
String cnpj,
String stateRegistration,
String email,
String phone,
String responsible

) {}
