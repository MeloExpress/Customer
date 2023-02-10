package br.com.MeloExpress.domain;

import br.com.MeloExpress.dto.AddressRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "addressId")
@Table(name = "address")
@Entity(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    private String zipCode;
    private String street;
    private String number;
    private String complements;
    private String district;
    private String city;
    private String state;
    private String pointReference;


    public Address (AddressRegisterDTO addressRegisterDTO) {
        this.zipCode = addressRegisterDTO.zipCode();
        this.street = addressRegisterDTO.street();
        this.number = addressRegisterDTO.number();
        this.complements = addressRegisterDTO.complements();
        this.district = addressRegisterDTO.district();
        this.city = addressRegisterDTO.city();
        this.state = addressRegisterDTO.state();
        this.pointReference = addressRegisterDTO.pointReference();
    }
}
