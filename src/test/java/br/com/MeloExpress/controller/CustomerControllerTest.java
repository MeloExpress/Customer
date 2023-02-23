package br.com.MeloExpress.controller;

import br.com.MeloExpress.dao.CustomerDAO;
import br.com.MeloExpress.domain.Customer;
import br.com.MeloExpress.dto.CustomerDetailsDTO;
import br.com.MeloExpress.dto.CustomerRegisterDTO;
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

        CustomerDAO customerDAO = mock(CustomerDAO.class);
        Customer savedCustomer = new Customer(customerRegisterDTO);
        when(customerDAO.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerController controller = new CustomerController();
        controller.customerDAO = customerDAO;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity response = controller.cadastrar(customerRegisterDTO, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new CustomerDetailsDTO(savedCustomer), response.getBody());
    }

}