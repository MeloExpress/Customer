package br.com.MeloExpress.Customer.service;

import br.com.MeloExpress.Customer.repository.CustomerRepository;
import br.com.MeloExpress.Customer.dto.*;
import br.com.MeloExpress.Customer.exceptions.CustomerNotFoundException;
import br.com.MeloExpress.Customer.domain.Address;
import br.com.MeloExpress.Customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }




    public Optional<CustomerDetailsFindDTO> findById(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            CustomerDetailsFindDTO customerDetails = new CustomerDetailsFindDTO(optionalCustomer.get());
            return Optional.of(customerDetails);
        } else {
            return Optional.empty();
        }
    }

    public Optional<CustomerDetailsFindDTO> findCustomerByCode(UUID customerCode) {
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerCode(customerCode);
        if (optionalCustomer.isPresent()) {
            CustomerDetailsFindDTO customerDetails = new CustomerDetailsFindDTO(optionalCustomer.get());
            return Optional.of(customerDetails);
        } else {
            return Optional.empty();
        }
    }



    public List<CustomerDetailsFindDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerDetailsFindDTO::new)
                .collect(Collectors.toList());
    }


    public Optional<CustomerDetailsFindDTO> updateCustomer(Long customerId, CustomerDetailsUpdateDTO customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setCompanyName(customerDetails.companyName());
            customer.setEmail(customerDetails.email());
            customer.setPhone(customerDetails.phone());
            customer.setResponsible(customerDetails.responsible());
            Customer updatedCustomer = customerRepository.save(customer);
            CustomerDetailsFindDTO customerDetailsDTO = new CustomerDetailsFindDTO(updatedCustomer);
            return Optional.of(customerDetailsDTO);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public CustomerDetailsDTO registerCustomer(CustomerRegisterDTO customerRegisterDTO) {
        Customer customer = new Customer(customerRegisterDTO);
        customer.setCustomerCode(UUID.randomUUID());
        List<Address> addresses = new ArrayList<>();
        for (AddressRegisterDTO addressRegisterDTO : customerRegisterDTO.addresses()) {
            Address address = new Address(addressRegisterDTO);
            address.setCustomer(customer);

            String cep = addressRegisterDTO.zipCode();
            RestTemplate restTemplate = new RestTemplate();
            String viaCepUrl = "https://viacep.com.br/ws/" + cep + "/json/";
            ViaCepDTO viaCepDTO = restTemplate.getForObject(viaCepUrl, ViaCepDTO.class);

            address.setZipCode(viaCepDTO.cep());
            address.setStreet(viaCepDTO.logradouro());
            address.setCity(viaCepDTO.localidade());
            address.setState(viaCepDTO.uf());
            address.setDistrict(viaCepDTO.bairro());

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