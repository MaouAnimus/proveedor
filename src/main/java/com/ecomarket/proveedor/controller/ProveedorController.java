package com.ecomarket.proveedor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.proveedor.model.Proveedor;
import com.ecomarket.proveedor.service.ProveedorService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping()
    public ResponseEntity<List<Proveedor>> getAll() {
    List<Proveedor> proveedores = proveedorService.findAll();
        if (!proveedores.isEmpty()) {
            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        }
        return new ResponseEntity<>(proveedores, HttpStatus.NO_CONTENT);
    }
    
    @PostMapping()
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        try {
            return new ResponseEntity<>(proveedorService.crearProveedor(proveedor),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }    
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getById(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Proveedor> updateById(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Proveedor provUpd = proveedorService.updateById(id, proveedor);
        if (provUpd != null) {
            return new ResponseEntity<>(provUpd, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
