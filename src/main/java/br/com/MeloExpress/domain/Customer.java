package br.com.MeloExpress.domain;


import br.com.MeloExpress.controller.dto.CustomerRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "customerId")
@Table(name = "customer")
@Entity(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String companyName;
    private String cnpj;
    private String stateRegistration;
    private String email;
    private String phone;
    private String responsible;

    private Address address;

    public Customer (CustomerRegisterDTO customerRegisterDTO) {
        this.companyName = customerRegisterDTO.companyName();
        this.cnpj = customerRegisterDTO.cnpj();
        this.stateRegistration = customerRegisterDTO.stateRegistration();
        this.email = customerRegisterDTO.email();
        this.phone = customerRegisterDTO.phone();
        this.responsible = customerRegisterDTO.Responsible();
    }

}
