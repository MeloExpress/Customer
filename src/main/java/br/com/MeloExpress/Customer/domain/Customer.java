package br.com.MeloExpress.Customer.domain;



import br.com.MeloExpress.Customer.dto.CustomerRegisterDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    @Column(name = "customer_code")
    private UUID customerCode;

    public Customer (CustomerRegisterDTO customerRegisterDTO) {
        this.companyName = customerRegisterDTO.companyName();
        this.cnpj = customerRegisterDTO.cnpj();
        this.stateRegistration = customerRegisterDTO.stateRegistration();
        this.email = customerRegisterDTO.email();
        this.phone = customerRegisterDTO.phone();
        this.responsible = customerRegisterDTO.responsible();
        this.active = customerRegisterDTO.active();
        this.customerCode = UUID.randomUUID();
    }

}
