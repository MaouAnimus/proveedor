package com.ecomarket.proveedor.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.proveedor.model.Proveedor;
import com.ecomarket.proveedor.repository.ProveedorRepository;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor crearProveedor(Proveedor proveedor) {
        if (proveedor != null) {
            return proveedorRepository.save(proveedor);
        }
        return null;
    }

    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Proveedor updateById(Long id, Proveedor proveedor) {
        Proveedor proveedorExistente = proveedorRepository.findById(id).orElse(null);
        if (proveedorExistente != null && proveedor != null) {
            if (proveedor.getNombreProveedor() != null) {
                proveedorExistente.setNombreProveedor(proveedor.getNombreProveedor());
            }
            proveedorRepository.save(proveedorExistente);
            return proveedorExistente;
        }
        return null;
    }
    
    public List<Proveedor> getAll() {
        List<Proveedor> prov = proveedorRepository.findAll();
        if (prov != null && !prov.isEmpty()) 
        {
            return prov;       
        }
        return Collections.emptyList();
    }
}
