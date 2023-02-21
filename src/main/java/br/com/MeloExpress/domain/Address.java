package br.com.MeloExpress.domain;

import br.com.MeloExpress.dto.AddressDetailsDTO;
import br.com.MeloExpress.dto.AddressRegisterDTO;
import br.com.MeloExpress.dto.AddressUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "addressId")
@Table(name = "address")
@Entity(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    private String zipCode;
    private String street;
    private String number;
    private String complements;
    private String district;
    private String city;
    private String state;
    private String pointReference;


    public void updateFromDTO(AddressUpdateDTO addressRegisterDTO) {
        if (addressRegisterDTO.getStreet() != null) {
            this.street = addressRegisterDTO.getStreet();
        }
        if (addressRegisterDTO.getNumber() != null) {
            this.number = addressRegisterDTO.getNumber();
        }
        if (addressRegisterDTO.getComplements() != null) {
            this.complements = addressRegisterDTO.getComplements();
        }
        if (addressRegisterDTO.getDistrict() != null) {
            this.district = addressRegisterDTO.getDistrict();
        }
        if (addressRegisterDTO.getCity() != null) {
            this.city = addressRegisterDTO.getCity();
        }
        if (addressRegisterDTO.getState() != null) {
            this.state = addressRegisterDTO.getState();
        }
        if (addressRegisterDTO.getZipCode() != null) {
            this.zipCode = addressRegisterDTO.getZipCode();
        }
    }
    public Address (AddressRegisterDTO addressRegisterDTO) {
        this.customer = new Customer();
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
