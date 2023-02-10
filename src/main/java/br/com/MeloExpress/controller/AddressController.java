package br.com.MeloExpress.controller;

import br.com.MeloExpress.controller.dao.AddressDAO;
import br.com.MeloExpress.controller.dto.AddressRegisterDTO;
import br.com.MeloExpress.controller.dto.AddressDetailsDTO;
import br.com.MeloExpress.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    public AddressDAO addressDAO;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@RequestBody AddressRegisterDTO addressRegisterDTO, UriComponentsBuilder uriBuilder){
        var address = new Address(addressRegisterDTO);
        addressDAO.save(address);
        var uri = uriBuilder.path("/address{id}").buildAndExpand(address.getStreet()).toUri();
        return ResponseEntity.created(uri).body(new AddressDetailsDTO(address));

    }
}
