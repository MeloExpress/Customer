package br.com.MeloExpress.controller;

import br.com.MeloExpress.dao.CustomerRepository;
import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.dto.CustomerDetailsDTO;
import br.com.MeloExpress.dto.CustomerRegisterDTO;
import br.com.MeloExpress.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CustomerControllerTest {


    @Test
    public void createCustomerTest() {

        CustomerRegisterDTO customerRegisterDTO = new CustomerRegisterDTO(
                "Joao da Silva LTDA",
                "23445671000100",
                "223445611779" ,
                "joaodasilva@meloexpress.com" ,
                "16994510990" ,
                "Joao da Silva",
                true
        );

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        Customer savedCustomer = new Customer(customerRegisterDTO);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerController controller = new CustomerController(new CustomerService(customerRepository));
        controller.customerService = new CustomerService(customerRepository);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity response = controller.createCustomer(customerRegisterDTO, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new CustomerDetailsDTO(savedCustomer), response.getBody());
    }

}