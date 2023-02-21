package br.com.MeloExpress.controller;

import br.com.MeloExpress.dto.CustomerDetailsDTO;
import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.dto.CustomerRegisterDTO;
import br.com.MeloExpress.dao.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    public CustomerDAO customerDAO;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@RequestBody  @Validated CustomerRegisterDTO customerRegisterDTO, UriComponentsBuilder uriBuilder){
        var customer = new Customer(customerRegisterDTO);
        customerDAO.save(customer);
        var uri = uriBuilder.path("/customer/{id}").buildAndExpand(customer.getCustomerId()).toUri();
        return ResponseEntity.created(uri).body(new CustomerDetailsDTO(customer));

    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId) {
        Optional<Customer> customer = customerDAO.findById(customerId);
        if(customer.isPresent()) {
            return ResponseEntity.ok().body(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
                                                   @RequestBody Customer customerDetails) {
        Optional<Customer> customer = customerDAO.findById(customerId);
        if(customer.isPresent()) {
            customer.get().setCompanyName(customerDetails.getCompanyName());
            customer.get().setEmail(customerDetails.getEmail());
            Customer updatedCustomer = customerDAO.save(customer.get());
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCustomer(@PathVariable(value = "id") Long customerId) {
        Optional<Customer> customer = customerDAO.findById(customerId);
        if(customer.isPresent()) {
            customer.get().setActive(false);
            customerDAO.save(customer.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/reactivate")
    public ResponseEntity reactivateCustomer(@PathVariable(value = "id") Long customerId) {
        Optional<Customer> customer = customerDAO.findById(customerId);
        if(customer.isPresent()) {
            customer.get().setActive(true);
            customerDAO.save(customer.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
