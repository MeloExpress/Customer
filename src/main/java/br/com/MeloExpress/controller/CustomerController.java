package br.com.MeloExpress.controller;

import br.com.MeloExpress.controller.dto.CustomerDetailsDTO;
import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.controller.dto.CustomerRegisterDTO;
import br.com.MeloExpress.controller.dao.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    public CustomerDAO customerDAO;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@RequestBody  CustomerRegisterDTO customerRegisterDTO, UriComponentsBuilder uriBuilder){
        var customer = new Customer(customerRegisterDTO);
        customerDAO.save(customer);
        var uri = uriBuilder.path("/customer/{id}").buildAndExpand(customer.getCustomerId()).toUri();
        return ResponseEntity.created(uri).body(new CustomerDetailsDTO(customer));

    }

}
