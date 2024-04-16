package com.example.projetsynthese.service;

import com.example.projetsynthese.callAPI.IGA;
import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.SuperCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private SuperC superC;
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

    public List<Product> getSuperCProduct(){
        return superC.getProduits();
    }

    public void updateSuperCProduct(){
        superC.getSupercDatas();
    }

}
