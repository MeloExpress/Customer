package br.com.MeloExpress.controller;


import br.com.MeloExpress.dto.CustomerDetailsDTO;
import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.dto.CustomerDetailsFindAllDTO;
import br.com.MeloExpress.dto.CustomerRegisterDTO;
import br.com.MeloExpress.exceptions.CustomerNotFoundException;
import br.com.MeloExpress.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public List<CustomerDetailsFindAllDTO> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if(customer.isPresent()) {
            return ResponseEntity.ok().body(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
                                                   @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
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
