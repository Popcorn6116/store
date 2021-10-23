package com.mhj.store.service;

import com.mhj.store.element.Address;

public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);

}
