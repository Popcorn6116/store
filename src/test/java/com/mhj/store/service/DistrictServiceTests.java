package com.mhj.store.service;

import com.mhj.store.element.Address;
import com.mhj.store.element.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

// @SpringBootTest:Indicates that the current class is a test class and will not be packaged with the project
@SpringBootTest
// @RunWith: start this test class
@RunWith(SpringRunner.class)
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void getByParent(){
        //86 means China, all provinces' parent is 86
        List<District> list = districtService.getByParent("86");
        for (District d: list) {
            System.err.println(d);
        }
    }
}
