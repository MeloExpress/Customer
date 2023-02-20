package br.com.MeloExpress.controller;

import br.com.MeloExpress.dao.AddressDAO;
import br.com.MeloExpress.dao.CustomerDAO;
import br.com.MeloExpress.dto.AddressRegisterDTO;
import br.com.MeloExpress.dto.AddressDetailsDTO;
import br.com.MeloExpress.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("customers/{customerId}/addresses")
public class AddressController {

    @Autowired
    public AddressDAO addressDAO;

    @Autowired
    public CustomerDAO customerDAO;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@PathVariable Long customerId, @RequestBody AddressRegisterDTO addressRegisterDTO, UriComponentsBuilder uriBuilder){
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

}

