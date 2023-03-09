package br.com.MeloExpress.Customer.controller;


import br.com.MeloExpress.Customer.dto.CustomerDetailsDTO;
import br.com.MeloExpress.Customer.dto.CustomerDetailsFindDTO;
import br.com.MeloExpress.Customer.dto.CustomerDetailsUpdateDTO;
import br.com.MeloExpress.Customer.dto.CustomerRegisterDTO;
import br.com.MeloExpress.Customer.exceptions.CustomerNotFoundException;
import br.com.MeloExpress.Customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDetailsDTO> createCustomer(@RequestBody @Validated CustomerRegisterDTO customerRegisterDTO, UriComponentsBuilder uriBuilder) {
        var customerDetailsDTO = customerService.registerCustomer(customerRegisterDTO);
        var uri = uriBuilder.path("/customers/{}").buildAndExpand(customerDetailsDTO.customerId()).toUri();
        return ResponseEntity.created(uri).body(customerDetailsDTO);
    }

    @GetMapping
    public List<CustomerDetailsFindDTO> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDetailsFindDTO> getCustomerById(@PathVariable Long customerId) {
        Optional<CustomerDetailsFindDTO> optionalCustomer = customerService.findById(customerId);
        if (optionalCustomer.isPresent()) {
            CustomerDetailsFindDTO customerDetails = optionalCustomer.get();
            return ResponseEntity.ok(customerDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDetailsFindDTO> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerDetailsUpdateDTO customerDetails) {
        Optional<CustomerDetailsFindDTO> optionalCustomerDetails = customerService.updateCustomer(customerId, customerDetails);
        if (optionalCustomerDetails.isPresent()) {
            return ResponseEntity.ok(optionalCustomerDetails.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/reactivate")
    public ResponseEntity reactivateCustomer(@PathVariable(value = "id") Long customerId) {
        try {
            customerService.reactivateCustomer(customerId);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
