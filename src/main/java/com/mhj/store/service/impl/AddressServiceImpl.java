package com.mhj.store.service.impl;

import com.mhj.store.element.Address;
import com.mhj.store.mapper.AddressMapper;
import com.mhj.store.service.IAddressService;
import com.mhj.store.service.IDistrictService;
import com.mhj.store.service.ex.AddressCountLimitException;
import com.mhj.store.service.ex.InsertException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if(count>=maxCount){
            throw new AddressCountLimitException("The number of receiving addresses exceeded the upper limit!");
        }

        address.setProvinceName(districtService.getNameByCode(address.getProvinceCode()));
        address.setCityName(districtService.getNameByCode(address.getCityCode()));
        address.setAreaName(districtService.getNameByCode(address.getAreaCode()));

        address.setUid(uid);
        Integer isDefault = count==0 ? 1 : 0;
        address.setIsDefault(isDefault);

        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if(rows != 1) {
            throw new InsertException("An unknown exception occurred while adding the receiving address!");
        }
    }
}
