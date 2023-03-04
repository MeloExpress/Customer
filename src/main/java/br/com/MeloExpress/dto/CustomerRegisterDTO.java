package br.com.MeloExpress.dto;

public record CustomerRegisterDTO(

String companyName,
String cnpj,
String stateRegistration,
String email,
String phone,
String responsible,
boolean active

) {}
