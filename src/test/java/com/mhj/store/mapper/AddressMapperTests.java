package com.mhj.store.mapper;

import com.mhj.store.element.Address;
import com.mhj.store.element.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

// @SpringBootTest:Indicates that the current class is a test class and will not be packaged with the project
@SpringBootTest
// @RunWith: start this test class
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(18);
        address.setPhone("1381385858");
        address.setName("MHJjjjjjjjj");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(18);
        System.out.println(count);
    }
}
