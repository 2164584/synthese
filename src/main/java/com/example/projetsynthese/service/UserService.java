package com.example.projetsynthese.service;

import com.example.projetsynthese.callAPI.IGA;
import com.example.projetsynthese.callAPI.Maxi;
import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.dto.ProductDTO;
import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    ProductRepository productRepository;

    public UserService() {
    }

    public List<Product> getIGAProduct(){
        return productRepository.findAllWithManufacturerIGA();
    }

    public List<Product> getMetroProduct() {
        return productRepository.findAllWithManufacturerMetro();
    }

    public List<Product> getSuperCProduct(){
        return productRepository.findAllWithManufacturerSuperC();
    }

    public List<Product> getMaxiProduct(){
        return productRepository.findAllWithManufacturerMaxi();
    }

    public void updateSuperCProduct(){
        if(!SuperC.isFetching)
            SuperC.getSupercDatas();
    }

    public void updateMetroProduct(){
        if(!Metro.isFecthing)
            Metro.getMetroDatas();
    }

    public void updateIGAProduct(){
        if(!IGA.isFetching)
            IGA.getSupercDatas();
    }

    public void updateMaxiProduct(){
        if(!Maxi.isFetching)
            Maxi.getMaxiDatas();
    }

    public float getSuperCPercent(){
        return SuperC.getPercent();
    }

    public float getIGAPercent(){
        return IGA.getPercent();
    }
}
