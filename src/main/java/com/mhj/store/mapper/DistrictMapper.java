package com.mhj.store.mapper;

import com.mhj.store.element.District;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictMapper {
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
