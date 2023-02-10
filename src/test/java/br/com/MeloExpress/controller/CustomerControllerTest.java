package br.com.MeloExpress.controller;

import br.com.MeloExpress.dao.CustomerDAO;
import br.com.MeloExpress.dto.CustomerDetailsDTO;
import br.com.MeloExpress.dto.CustomerRegisterDTO;
import br.com.MeloExpress.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @Test
    public void testCadastrar() {
        
        CustomerRegisterDTO customerRegisterDTO = new CustomerRegisterDTO();
        
        
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        Customer savedCustomer = new Customer(customerRegisterDTO);
        when(customerDAO.save(any(Customer.class))).thenReturn(savedCustomer);

        
        CustomerController controller = new CustomerController();
        controller.customerDAO = customerDAO;

       
        ResponseEntity response = controller.cadastrar(customerRegisterDTO, null);

    
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new CustomerDetailsDTO(savedCustomer), response.getBody());
    }

}