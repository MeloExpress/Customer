package br.com.MeloExpress.domain;


import br.com.MeloExpress.dto.CustomerRegisterDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "customerId")
@Table(name = "customer")
@Entity(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    private String companyName;
    private String cnpj;
    private String stateRegistration;
    private String email;
    private String phone;
    private String responsible;
    private boolean active=true;


    public Customer (CustomerRegisterDTO customerRegisterDTO) {
        this.companyName = customerRegisterDTO.companyName();
        this.cnpj = customerRegisterDTO.cnpj();
        this.stateRegistration = customerRegisterDTO.stateRegistration();
        this.email = customerRegisterDTO.email();
        this.phone = customerRegisterDTO.phone();
        this.responsible = customerRegisterDTO.responsible();
        this.active = customerRegisterDTO.active();
    }

}
