package com.mhj.store.service;

import com.mhj.store.element.District;

import java.util.List;

public interface IDistrictService {
    List<District> getByParent(String parent);

    String getNameByCode(String code);
}
