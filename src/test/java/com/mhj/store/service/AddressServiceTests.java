package com.mhj.store.service;

import com.mhj.store.element.Address;
import com.mhj.store.element.User;
import com.mhj.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// @SpringBootTest:Indicates that the current class is a test class and will not be packaged with the project
@SpringBootTest
// @RunWith: start this test class
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("111111111");
        address.setName("BABA");
        addressService.addNewAddress(18,"baba", address);
    }





}
