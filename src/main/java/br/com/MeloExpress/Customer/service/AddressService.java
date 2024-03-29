package br.com.MeloExpress.Customer.service;

import br.com.MeloExpress.Customer.dto.CustomerDetailsFindDTO;
import br.com.MeloExpress.Customer.repository.AddressRepository;
import br.com.MeloExpress.Customer.repository.CustomerRepository;
import br.com.MeloExpress.Customer.domain.Address;
import br.com.MeloExpress.Customer.dto.AddressDetailsDTO;
import br.com.MeloExpress.Customer.dto.AddressRegisterDTO;
import br.com.MeloExpress.Customer.dto.AddressUpdateDTO;
import br.com.MeloExpress.Customer.exceptions.AddressNotFoundException;
import br.com.MeloExpress.Customer.exceptions.CustomerNotFoundException;
import br.com.MeloExpress.Customer.domain.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Transactional
@Service
public class AddressService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public AddressService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }


    public ResponseEntity<AddressDetailsDTO> createAddress(Long customerId, AddressRegisterDTO addressRegisterDTO, UriComponentsBuilder uriBuilder) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado."));
        Address address = new Address(addressRegisterDTO);
        address.setAddressCode(UUID.randomUUID());
        address.setCustomer(customer);
        addressRepository.save(address);
        URI uri = uriBuilder.path("/customers/{customerId}/addresses/{addressId}")
                .buildAndExpand(customerId, address.getAddressId()).toUri();
        return ResponseEntity.created(uri).body(new AddressDetailsDTO(address));
    }

    public Optional<AddressDetailsDTO> findByAddressCode(UUID addressCode) {
        Optional<Address> optionalAddress = addressRepository.findByAddressCode(addressCode);
        if (optionalAddress.isPresent()) {
            AddressDetailsDTO addressDetails = new AddressDetailsDTO(optionalAddress.get());
            return Optional.of(addressDetails);
        } else {
            return Optional.empty();
        }
    }

    public List<AddressDetailsDTO> getAddressesByCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente com o ID " + customerId + " não encontrado."));
        List<Address> addresses = addressRepository.findByCustomer(customer);
        return addresses.stream().map(AddressDetailsDTO::new).collect(Collectors.toList());
    }


    public AddressDetailsDTO updateAddress(Long customerId, Long addressId, AddressUpdateDTO addressUpdateDTO)
            throws CustomerNotFoundException, AddressNotFoundException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente com o ID " + customerId + " não encontrado."));

        Address address = addressRepository.findByAddressIdAndCustomerCustomerIdAndCustomerActiveTrue(addressId, customer.getCustomerId())
                .orElseThrow(() -> new AddressNotFoundException("Endereço com o ID " + addressId + " não encontrado para o cliente com o ID " + customerId));

        address.updateFromDTO(addressUpdateDTO);
        Address updatedAddress = addressRepository.save(address);

        return new AddressDetailsDTO(updatedAddress);
    }

    public AddressDetailsDTO getAddressDetailsByCustomerAndAddressId(Long customerId, Long addressId) throws CustomerNotFoundException, AddressNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

        Address address = addressRepository.findByAddressIdAndCustomerCustomerIdAndCustomerActiveTrue(addressId, customer.getCustomerId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id " + addressId + " for customer " + customerId));

        return new AddressDetailsDTO(address);
    }


    public void deleteAddress(Long customerId, Long addressId) throws CustomerNotFoundException, AddressNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

        Address address = addressRepository.findByAddressIdAndCustomerCustomerIdAndCustomerActiveTrue(addressId, customerId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id " + addressId + " for customer " + customerId));

        addressRepository.delete(address);
    }

}