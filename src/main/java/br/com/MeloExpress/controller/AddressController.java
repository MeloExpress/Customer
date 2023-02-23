package br.com.MeloExpress.controller;

import br.com.MeloExpress.dao.AddressDAO;
import br.com.MeloExpress.dao.CustomerDAO;
import br.com.MeloExpress.dto.AddressRegisterDTO;
import br.com.MeloExpress.dto.AddressDetailsDTO;
import br.com.MeloExpress.domain.Address;
import br.com.MeloExpress.dto.AddressUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers/{customerId}/addresses")
public class AddressController {

    @Autowired
    public AddressDAO addressDAO;

    @Autowired
    public CustomerDAO customerDAO;


    @PostMapping
    @Transactional
    public ResponseEntity createAddress (@PathVariable Long customerId, @RequestBody AddressRegisterDTO addressRegisterDTO, UriComponentsBuilder uriBuilder){
        var customer = customerDAO.findById(customerId).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        var address = new Address(addressRegisterDTO);
        address.setCustomer(customer);
        addressDAO.save(address);
        var uri = uriBuilder.path("/customers/{customerId}/addresses/{addressId}").buildAndExpand(customerId, address.getAddressId()).toUri();
        return ResponseEntity.created(uri).body(new AddressDetailsDTO(address));
    }

    @GetMapping
    public ResponseEntity<List<AddressDetailsDTO>> getAddressesByCustomer(@PathVariable Long customerId) {
        var customer = customerDAO.findById(customerId).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        var addresses = addressDAO.findByCustomer(customer);
        var addressDetailsDTOList = addresses.stream().map(AddressDetailsDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(addressDetailsDTOList);
    }

    @PutMapping("/{addressId}")
    @Transactional
    public ResponseEntity<AddressDetailsDTO> updateAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId,
            @RequestBody AddressUpdateDTO addressUpdateDTO
    ) {
        var customer = customerDAO.findById(customerId).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<Address> optionalAddress = addressDAO.findByIdAndCustomer(addressId, customerId);
        if (optionalAddress.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Address address = optionalAddress.get();
        address.updateFromDTO(addressUpdateDTO);
        addressDAO.save(address);
        return ResponseEntity.ok(new AddressDetailsDTO(address));
    }

}


