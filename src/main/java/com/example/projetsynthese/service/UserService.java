package com.example.projetsynthese.service;

import com.example.projetsynthese.callAPI.IGA;
import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private SuperC superC = new SuperC();
    private Metro metro = new Metro();
    private IGA iga = new IGA();

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

}
