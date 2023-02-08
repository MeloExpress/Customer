package br.com.MeloExpress.controller.dto;

public record CustomerRegisterDTO(

String companyName,
String cnpj,
String stateRegistration,
String email,
String phone,
String Responsible,

AddressRegisterDTO address

) {}
