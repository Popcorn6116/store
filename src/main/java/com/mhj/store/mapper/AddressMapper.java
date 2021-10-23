package com.mhj.store.mapper;

import com.mhj.store.element.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressMapper {

    //return  numbers means how many row are influenced
    Integer insert(Address address);

    Integer countByUid(Integer uid);
}
