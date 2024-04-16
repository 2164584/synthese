package com.example.projetsynthese.service;

import com.example.projetsynthese.callAPI.IGA;
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
    private ProductRepository productRepository;

    @Autowired
    private Metro metro;
    @Autowired
    private IGA iga;

    public UserService() {
    }

    public List<Product> getIGAProduct(){
        return iga.getProduits();
    }

    public List<Product> getMetroProduct() {
        return metro.getProduits();
    }

    public List<ProductDTO> getSuperCProduct(){
        return productRepository.findAllWithManufacturerSuperC();
    }

    public void updateSuperCProduct(){
        if(!SuperC.isFecthing)
            SuperC.getSupercDatas();
    }

}
