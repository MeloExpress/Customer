package br.com.MeloExpress.service;

import br.com.MeloExpress.dao.CustomerRepository;
import br.com.MeloExpress.domain.Address;
import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.dto.AddressRegisterDTO;
import br.com.MeloExpress.dto.CustomerDetailsDTO;
import br.com.MeloExpress.dto.CustomerRegisterDTO;
import br.com.MeloExpress.exceptions.CustomerNotFoundException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }



    public List<CustomerDetailsDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerDetailsDTO::new)
                .collect(Collectors.toList());
    }


    public Customer updateCustomer(Long customerId, Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setCompanyName(customerDetails.getCompanyName());
            customer.setEmail(customerDetails.getEmail());
            return customerRepository.save(customer);
        } else {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + customerId);
        }
    }

    @Transactional
    public CustomerDetailsDTO registerCustomer(CustomerRegisterDTO customerRegisterDTO) {
        Customer customer = new Customer(customerRegisterDTO);
        List<Address> addresses = new ArrayList<>();
        for (AddressRegisterDTO addressRegisterDTO : customerRegisterDTO.addresses()) {
            Address address = new Address(addressRegisterDTO);
            address.setCustomer(customer);
            addresses.add(address);
        }
        customer.setAddresses(addresses);
        customer = customerRepository.save(customer);
        return new CustomerDetailsDTO(customer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (customer.isActive()) {
                customer.setActive(false);
                customerRepository.save(customer);
            } else {
                throw new CustomerNotFoundException("Cliente com o ID " + customerId + " já está inativo.");
            }
        } else {
            throw new CustomerNotFoundException("Cliente com o ID " + customerId + " não encontrado.");
        }
    }

    @Transactional
    public void reactivateCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (!customer.isActive()) {
                customer.setActive(true);
                customerRepository.save(customer);
            } else {
                throw new CustomerNotFoundException("Cliente com o ID " + customerId + " já está ativo.");
            }
        } else {
            throw new CustomerNotFoundException("Cliente com o ID " + customerId + " não encontrado.");
        }
    }

}