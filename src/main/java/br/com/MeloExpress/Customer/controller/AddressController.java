package br.com.MeloExpress.Customer.controller;


import br.com.MeloExpress.Customer.dto.AddressDetailsDTO;
import br.com.MeloExpress.Customer.dto.AddressRegisterDTO;
import br.com.MeloExpress.Customer.dto.AddressUpdateDTO;
import br.com.MeloExpress.Customer.dto.CustomerDetailsFindDTO;
import br.com.MeloExpress.Customer.exceptions.AddressNotFoundException;
import br.com.MeloExpress.Customer.exceptions.CustomerNotFoundException;
import br.com.MeloExpress.Customer.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("customers/{customerId}/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity createAddress (@PathVariable Long customerId, @RequestBody AddressRegisterDTO addressRegisterDTO, UriComponentsBuilder uriBuilder) {
        return addressService.createAddress(customerId, addressRegisterDTO, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<List<AddressDetailsDTO>> getAddressesByCustomer(@PathVariable Long customerId) {
        try {
            List<AddressDetailsDTO> addressDetailsDTOList = addressService.getAddressesByCustomer(customerId);
            return ResponseEntity.ok().body(addressDetailsDTOList);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/code/{addressCode}")
    public ResponseEntity<AddressDetailsDTO> findByCode(@PathVariable UUID addressCode) {
        Optional<AddressDetailsDTO> optionalAddressDetails = addressService.findByAddressCode(addressCode);
        if (optionalAddressDetails.isPresent()) {
            return ResponseEntity.ok(optionalAddressDetails.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDetailsDTO> updateAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId,
            @RequestBody AddressUpdateDTO addressUpdateDTO) {

        try {
            AddressDetailsDTO updatedAddress = addressService.updateAddress(customerId, addressId, addressUpdateDTO);
            return ResponseEntity.ok(updatedAddress);
        } catch (CustomerNotFoundException | AddressNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDetailsDTO> getAddressDetailsByCustomerAndAddressId(@PathVariable Long customerId, @PathVariable Long addressId) {
        try {
            AddressDetailsDTO addressDetailsDTO = addressService.getAddressDetailsByCustomerAndAddressId(customerId, addressId);
            return ResponseEntity.ok().body(addressDetailsDTO);
        } catch (CustomerNotFoundException | AddressNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        try {
            addressService.deleteAddress(customerId, addressId);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException | AddressNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}


