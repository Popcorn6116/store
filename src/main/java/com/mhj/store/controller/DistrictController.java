package com.mhj.store.controller;


import com.mhj.store.element.District;
import com.mhj.store.service.IAddressService;
import com.mhj.store.service.IDistrictService;
import com.mhj.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController{

    @Autowired
    private IDistrictService districtService;

    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<List<District>>(OK, data);
    }
}