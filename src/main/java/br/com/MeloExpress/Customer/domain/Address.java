package br.com.MeloExpress.Customer.domain;

import br.com.MeloExpress.Customer.dto.AddressRegisterDTO;
import br.com.MeloExpress.Customer.dto.AddressUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "addressId")
@Table(name = "address")
@Entity(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @ManyToOne
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


    public void updateFromDTO(AddressUpdateDTO addressUpdateDTO) {
        if (addressUpdateDTO.getStreet() != null) {
            this.street = addressUpdateDTO.getStreet();
        }
        if (addressUpdateDTO.getNumber() != null) {
            this.number = addressUpdateDTO.getNumber();
        }
        if (addressUpdateDTO.getComplements() != null) {
            this.complements = addressUpdateDTO.getComplements();
        }
        if (addressUpdateDTO.getDistrict() != null) {
            this.district = addressUpdateDTO.getDistrict();
        }
        if (addressUpdateDTO.getCity() != null) {
            this.city = addressUpdateDTO.getCity();
        }
        if (addressUpdateDTO.getState() != null) {
            this.state = addressUpdateDTO.getState();
        }
        if (addressUpdateDTO.getZipCode() != null) {
            this.zipCode = addressUpdateDTO.getZipCode();
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
